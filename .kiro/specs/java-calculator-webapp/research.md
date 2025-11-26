# Research & Design Decisions

---
**Purpose**: Java電卓webアプリの技術設計に関する調査結果、アーキテクチャ検討、および設計決定の根拠を記録する。

**Usage**: 設計ドキュメント（design.md）の背景情報として参照され、将来の監査や再利用のための証跡を提供する。
---

## Summary
- **Feature**: `java-calculator-webapp`
- **Discovery Scope**: New Feature（新規開発・greenfield）
- **Key Findings**:
  - モダンなJava webアプリケーションは3層アーキテクチャ（Presentation、Business、Data）とSPA（Single Page Application）パターンを組み合わせることが標準
  - Spring BootによるREST APIとフロントエンド分離が推奨される設計パターン
  - 型安全性、ステートレス設計、適切なHTTPステータスコード使用がベストプラクティス

## Research Log

### Java Webアプリケーションアーキテクチャの最新動向

- **Context**: Java電卓webアプリに適したアーキテクチャパターンの選定
- **Sources Consulted**:
  - [Web Application Architecture Guide: Best Practices, Trends 2024](https://cloud.folio3.com/blog/web-application-architecture-guide/)
  - [Web Application Architecture: The Latest Guide (2025 AI Update)](https://www.clickittech.com/devops/web-application-architecture/)
  - [Modern Web Application Architecture in 2025](https://acropolium.com/blog/modern-web-app-architecture/)

- **Findings**:
  - **3層アーキテクチャ**が電卓アプリケーションの標準構造:
    - Presentation Layer（HTML/CSS/JavaScript）
    - Application Layer（ビジネスロジック - Java）
    - Data Layer（永続化 - 本アプリでは最小限）
  - 将来性のある2025年のアーキテクチャは、マイクロサービス、サーバーレス、セキュリティベストプラクティスを含む
  - Javaは大規模エンタープライズアプリに適しているが、電卓のようなシンプルなアプリでも型安全性と堅牢性のメリットがある

- **Implications**:
  - フロントエンドとバックエンドの明確な分離を設計の基本原則とする
  - Spring Bootによる標準的なレイヤー構造（Controller → Service → Repository）を採用
  - 将来の拡張性（複雑な計算、計算履歴など）を考慮した設計

### Spring Boot REST API設計パターン

- **Context**: 電卓機能をRESTful APIとして公開する際のベストプラクティス
- **Sources Consulted**:
  - [Spring Boot REST Calculator (GitHub)](https://github.com/tgrip/rest-calculator)
  - [REST API Best Practices - Spring Boot Tutorial](https://www.springboottutorial.com/rest-api-best-practices-with-java-and-spring)
  - [How can I create a RESTful calculator? (Stack Overflow)](https://stackoverflow.com/questions/8275209/how-can-i-create-a-restful-calculator)

- **Findings**:
  - **レイヤー構造**: Controller → Service → Repository → Entity
  - **アノテーション**: `@RestController`, `@RequestMapping`, `@GetMapping`/`@PostMapping`, `@RequestParam`
  - **ステートレス設計**: すべてのクライアントリクエストに必要な情報を含める
  - **HTTPステータスコード**: 適切なステータスコード（200 OK、400 Bad Request、404 Not Found、500 Internal Server Error）を返す
  - **エンドポイント設計**: `/api/calculator/add?a=5&b=3` のようなクエリパラメータ、または `/api/calculator/calculate` へのPOSTリクエストでJSON本文

- **Implications**:
  - CalculatorControllerでREST APIエンドポイントを定義
  - CalculatorServiceでビジネスロジック（四則演算、エラーハンドリング）を実装
  - 入力検証をコントローラー層で実施し、適切なエラーレスポンスを返す

### フロントエンド・バックエンド分離とSPA

- **Context**: ユーザーインターフェースとバックエンドAPIの分離戦略
- **Sources Consulted**:
  - [Web Application Architecture Explained (Golden Owl)](https://goldenowl.asia/blog/web-application-architecture)
  - [Headless Architecture with Separated UI](https://medium.com/design-microservices-architecture-with-patterns/headless-architecture-with-separated-ui-for-backend-and-frontend-f9789920e112)
  - [API-Driven Single Page Applications](https://configr.medium.com/api-driven-single-page-applications-888b1335a42f)

- **Findings**:
  - **SPA（Single Page Application）**: 動的にコンテンツを更新し、ページ全体をリロードせず高速な遷移を実現
  - **フロントエンド技術**: React、Vue、Angularなどのフレームワーク、またはVanilla JavaScript
  - **API通信**: REST APIまたはGraphQLを介した非同期通信
  - **メリット**: 柔軟性、スケーラビリティ、保守性の向上、フロントエンドとバックエンドの独立した開発

- **Implications**:
  - フロントエンドは静的HTML/CSS/JavaScriptとして提供し、ブラウザで動作
  - バックエンドREST APIとJSON形式で通信
  - 電卓UIはJavaScriptでDOMを操作し、ユーザー入力をAPIに送信して結果を表示
  - シンプルさを重視し、Vanilla JavaScriptを採用（ReactやVueは過剰）

### セキュリティとエラーハンドリング

- **Context**: Web電卓アプリケーションのセキュリティとエラー処理
- **Findings**:
  - **入力検証**: すべてのユーザー入力をサーバーサイドで検証（数値の妥当性、範囲チェック）
  - **CSRF対策**: Spring Securityによる保護（今回は静的コンテンツのため最小限）
  - **XSS対策**: ユーザー入力をエスケープ（電卓では数値のみのためリスクは低い）
  - **エラーハンドリング**: ゼロ除算、数値オーバーフロー、無効な入力に対する適切なエラーメッセージ

- **Implications**:
  - CalculatorServiceで入力検証とエラーハンドリングを実装
  - 適切なHTTPステータスコードとエラーメッセージをJSONレスポンスで返す
  - フロントエンドでユーザーフレンドリーなエラー表示

### 式パーサーの実装方法（更新：要件3.2対応）

- **Context**: 要件3.2「複雑な式（例：2 + 3 * 4）を評価し、演算の優先順位を正しく処理」に対応するための式パーサー実装方法の調査
- **Sources Consulted**:
  - [Shunting yard algorithm (Wikipedia)](https://en.wikipedia.org/wiki/Shunting_yard_algorithm)
  - [Java Program to Implement Shunting Yard Algorithm (GeeksforGeeks)](https://www.geeksforgeeks.org/java/java-program-to-implement-shunting-yard-algorithm/)
  - [GitHub - ed-cooper/ShuntingYard](https://github.com/ed-cooper/ShuntingYard) — Javaによる実装例（括弧、結合規則、関数サポート）
  - [How to evaluate a math expression given in string form? (Stack Overflow)](https://stackoverflow.com/questions/3422673/how-to-evaluate-a-math-expression-given-in-string-form)
  - [mXparser](https://mathparser.org/) — 高機能な数式パーサーライブラリ

- **Findings**:
  - **Shunting-yardアルゴリズム**: Dijkstraが考案した中置記法（infix）を逆ポーランド記法（RPN）に変換するアルゴリズム
    - 演算子の優先順位と結合規則を正しく処理
    - 3ステップ: Lexer（字句解析）→ Parser（構文解析）→ Evaluator（評価）
    - スタックベースの実装で効率的
  - **既存Javaライブラリ**:
    - **exp4j**: Shunting-yardベース、Apache License 2.0、軽量（25KB）、シンプルな式評価に最適
    - **EvalEx**: ハンディな式評価器、数学関数と真偽値式をサポート
    - **mXparser**: 高機能（ユーザー定義関数、変数、反復計算）だがオーバースペック
    - **MathEval**: 超軽量（20KB）、基本演算のみ
  - **自前実装 vs ライブラリ使用**:
    - 自前実装: 学習目的、完全なカスタマイズ、依存関係ゼロ
    - ライブラリ使用: 迅速、テスト済み、バグが少ない

- **Implications**:
  - 電卓アプリのシンプルさを考慮し、**exp4j**ライブラリの使用を推奨
  - 依存関係を最小限にしたい場合は、Shunting-yardの軽量実装を検討
  - 式評価機能をExpressionParserコンポーネントとして分離し、CalculatorServiceから呼び出す
  - 構文エラー（括弧の不一致、無効な演算子配置）のハンドリングが必要

## Architecture Pattern Evaluation

| Option | Description | Strengths | Risks / Limitations | Notes |
|--------|-------------|-----------|---------------------|-------|
| 3層アーキテクチャ + SPA | Presentation（HTML/CSS/JS）、Business（Spring Boot）、Data（最小限）の分離 | 明確な責任分離、スケーラブル、保守性が高い | 小規模アプリには過剰な可能性 | 業界標準、将来の拡張性を考慮 |
| モノリシック（JSP/Thymeleaf） | サーバーサイドレンダリングでHTMLを生成 | シンプル、サーバー側で完結 | フロントエンド・バックエンド結合、UX劣る | 現代的でない、要件のレスポンシブデザインに不適 |
| フル・マイクロサービス | 計算サービス、UI サービスを分離 | 高いスケーラビリティ | 電卓アプリには過剰、複雑性増大 | 小規模アプリには不要 |

**選択**: 3層アーキテクチャ + SPA（Vanilla JavaScript）

**理由**:
- 要件5.4「JavaScriptを使用してクライアントサイドで計算を実行」に合致
- フロントエンド・バックエンド分離により、独立した開発・テストが可能
- モダンなベストプラクティスに準拠
- 将来的な機能拡張（計算履歴、複雑な演算）に対応可能
- シンプルさを保ちつつ、業界標準に従う

## Design Decisions

### Decision: Spring BootによるREST APIバックエンド

- **Context**: 電卓の四則演算をWeb APIとして提供する必要がある
- **Alternatives Considered**:
  1. Spring Boot + REST API — 標準的なJava webフレームワーク
  2. Jakarta EE（旧Java EE） — エンタープライズ仕様だが重量級
  3. Lightweight フレームワーク（Micronaut、Quarkus） — 最新だが学習コスト

- **Selected Approach**: Spring Boot + REST API
  - Controller層でHTTPリクエストを受け付け、`@RequestParam`で数値を取得
  - Service層で四則演算とエラーハンドリングを実装
  - JSON形式でレスポンスを返す（`{"result": 8.0}` または `{"error": "ゼロ除算"}"`）

- **Rationale**:
  - Spring Bootは成熟したエコシステムで、ドキュメントが豊富
  - `@RestController`アノテーションでREST APIの実装が容易
  - 組み込みTomcatによる簡単なデプロイ
  - Spring Boot Starterによる依存関係管理の簡素化

- **Trade-offs**:
  - **メリット**: 安定性、サポート、コミュニティの大きさ
  - **妥協点**: 軽量フレームワークに比べて起動時間がやや長い（電卓アプリには影響小）

- **Follow-up**:
  - Spring Boot 3.x（最新安定版）を使用
  - Java 17以上を推奨（LTSバージョン）

### Decision: Vanilla JavaScriptによるフロントエンド実装

- **Context**: 要件2および5でレスポンシブなUI、クライアントサイド実行が求められる
- **Alternatives Considered**:
  1. Vanilla JavaScript — 依存関係なし、シンプル
  2. React — コンポーネントベース、学習コスト高
  3. Vue.js — Reactより軽量、ただし依存関係増加

- **Selected Approach**: Vanilla JavaScript（HTML/CSS/JS）
  - ボタンクリックイベントでユーザー入力を収集
  - `fetch` APIでバックエンドREST APIを呼び出し
  - DOMを操作して結果を表示

- **Rationale**:
  - 電卓UIはシンプルで、React/Vueのような複雑なフレームワークは不要
  - 依存関係ゼロで、ページロード時間最小化（要件5.3「3秒以内に操作可能」）
  - 学習コストが低く、保守が容易

- **Trade-offs**:
  - **メリット**: 軽量、高速、シンプル
  - **妥協点**: 大規模化時のコード管理が困難（今回の電卓アプリではスコープ外）

- **Follow-up**:
  - ES6+の文法を使用（`const`, `let`, アロー関数、`async/await`）
  - レスポンシブデザインのためCSS Grid/Flexboxを活用

### Decision: ステートレスREST API設計

- **Context**: クライアント・サーバー間の通信方式
- **Selected Approach**: ステートレスREST API
  - すべてのリクエストに必要な情報（演算子、オペランド）を含める
  - サーバー側でセッション状態を保持しない
  - 各APIコールが独立

- **Rationale**:
  - REST APIのベストプラクティス
  - スケーラビリティとキャッシング可能性の向上
  - サーバー負荷の軽減

- **Trade-offs**:
  - **メリット**: シンプル、スケーラブル、テスト容易
  - **妥協点**: 連続計算（要件3.4）はクライアント側で前回結果を保持

- **Follow-up**:
  - 計算履歴が必要な場合は、クライアント側のlocalStorageを使用（将来拡張）

### Decision: エラーハンドリング戦略

- **Context**: 要件4で定義されたエラーケース（ゼロ除算、オーバーフロー、無効入力）への対応
- **Selected Approach**: 多層エラーハンドリング
  - **フロントエンド**: 基本的な入力検証（数値形式チェック）
  - **バックエンド**: 包括的な検証とビジネスロジックエラー処理
  - **HTTPステータスコード**:
    - 200 OK: 正常な計算結果
    - 400 Bad Request: 無効な入力
    - 422 Unprocessable Entity: ビジネスロジックエラー（ゼロ除算）
    - 500 Internal Server Error: サーバー内部エラー

- **Rationale**:
  - クライアント側で即座にフィードバック（UX向上）
  - サーバー側で信頼できる検証（セキュリティ）
  - 適切なHTTPステータスコードでエラーの種類を明確化

- **Trade-offs**:
  - **メリット**: 堅牢性、セキュリティ、ユーザーエクスペリエンス
  - **妥協点**: 二重検証によるコード重複（許容範囲内）

## Risks & Mitigations

- **リスク1: 数値精度の問題** — JavaのdoubleおよびJavaScriptのNumberは浮動小数点数であり、精度誤差が発生する可能性
  - **緩和策**: BigDecimal（Java）および適切な丸め処理、小数点以下8桁までの精度制限（要件3.3）

- **リスク2: CORS（Cross-Origin Resource Sharing）問題** — フロントエンドとバックエンドが異なるオリジンで動作する場合
  - **緩和策**: Spring BootでCORS設定を有効化（`@CrossOrigin`アノテーションまたはグローバル設定）

- **リスク3: クライアント側の計算との不一致** — 要件5.4「JavaScriptを使用してクライアントサイドで計算を実行」とバックエンドAPIの矛盾
  - **緩和策**: 要件を「クライアント側でUIロジックを実行、計算処理はバックエンドAPI」と解釈

- **リスク4: ブラウザ互換性** — 要件5.1で複数のブラウザ（Chrome、Firefox、Safari、Edge）をサポート
  - **緩和策**: ES6+のポリフィルまたはBabelトランスパイル、モダンブラウザの標準APIのみ使用

## References

### アーキテクチャとベストプラクティス
- [Web Application Architecture Guide: Best Practices, Trends 2024](https://cloud.folio3.com/blog/web-application-architecture-guide/)
- [Web Application Architecture: The Latest Guide (2025 AI Update)](https://www.clickittech.com/devops/web-application-architecture/)
- [Modern Web Application Architecture in 2025](https://acropolium.com/blog/modern-web-app-architecture/)

### Spring Boot REST API
- [GitHub - tgrip/rest-calculator](https://github.com/tgrip/rest-calculator) — Spring Boot電卓の実装例
- [REST API Best Practices - Spring Boot Tutorial](https://www.springboottutorial.com/rest-api-best-practices-with-java-and-spring)
- [How can I create a RESTful calculator? (Stack Overflow)](https://stackoverflow.com/questions/8275209/how-can-i-create-a-restful-calculator)

### フロントエンド・バックエンド分離
- [Web Application Architecture Explained (Golden Owl)](https://goldenowl.asia/blog/web-application-architecture)
- [Headless Architecture with Separated UI](https://medium.com/design-microservices-architecture-with-patterns/headless-architecture-with-separated-ui-for-backend-and-frontend-f9789920e112)
- [API-Driven Single Page Applications](https://configr.medium.com/api-driven-single-page-applications-888b1335a42f)

### Java技術
- [GitHub - in28minutes/java-best-practices](https://github.com/in28minutes/java-best-practices) — Java開発ベストプラクティス
