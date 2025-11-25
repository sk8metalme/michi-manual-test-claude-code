# E2E Test Specification: java-calculator-webapp e2eテスト

**Author**: Auto-generated
**Date**: 2025-11-25
**Version**: 1.0

## 1. Overview

### 1.1 Purpose
java-calculator-webappのエンドユーザーシナリオが完全に動作することを確認する

Example: To verify that end users can successfully complete critical user journeys in the  from start to finish in a real browser environment.

### 1.2 Scope
java-calculator-webappの全ユーザーフロー

Example: This test specification covers the complete user registration and login flow, including UI interactions, form validations, and successful authentication.

### 1.3 Testing Tool
- **Tool**: Vitest
- **Version**: 1.0.0

Example:
- **Tool**: Playwright / Selenium WebDriver / Cypress
- **Version**: 1.40.0 / 4.15.0 / 13.6.0

## 2. Test Environment

### 2.1 Software Requirements
- Browser Automation Tool:  1.0.0
- Browsers: 
- Application Environment: 
- Backend API: 
- Test Data Management: 

Example:
- Browser Automation Tool: Playwright 1.40.0
- Browsers: Chrome 120, Firefox 121, Safari 17
- Application Environment: [https://staging.example.com](https://staging.example.com)
- Backend API: [https://api-staging.example.com](https://api-staging.example.com)
- Test Data Management: Test database with seeded data

### 2.2 Hardware Requirements
- Test Machine: 
- Display Resolution: 
- Network: 

Example:
- Test Machine: macOS/Windows/Linux with 8GB RAM
- Display Resolution: 1920x1080 (Desktop), 768x1024 (Tablet), 375x667 (Mobile)
- Network: Stable internet connection (minimum 10 Mbps)

### 2.3 Test Data
- Test user accounts: ``
- Test data setup script: ``
- Data cleanup script: ``
- Environment variables: ``

## 3. User Flows

### 3.1 User Journey Map

```text
 →  →  →  → 
```

Example:

```text
Landing Page → Sign Up Form → Email Verification → Profile Setup → Dashboard
```

### 3.2 User Flow Details

| Flow ID | Flow Name | Description | Priority | Steps |
|---------|-----------|-------------|----------|-------  | High/Medium/Low   |  | High/Medium/Low |  |

Example:

| Flow ID | Flow Name | Description | Priority | Steps |
|---------|-----------|-------------|----------|-------|
| UF-001 | User Registration | New user signs up and verifies email | High | 5 |
| UF-002 | Product Purchase | User browses, adds to cart, and completes checkout | High | 8 |
| UF-003 | Password Reset | User resets forgotten password | Medium | 4 |

### 3.3 Browser/Device Matrix

Test each user flow on the following combinations:

| Browser | Version | Desktop | Tablet | Mobile | Priority |
|---------|---------|---------|--------|--------|----------|
| Chrome |  | ✓ | ✓ | ✓ | High |
| Firefox |  | ✓ | - | - | Medium |
| Safari |  | ✓ | ✓ | ✓ | High |
| Edge |  | ✓ | - | - | Low |

Example:

| Browser | Version | Desktop | Tablet | Mobile | Priority |
|---------|---------|---------|--------|--------|----------|
| Chrome | 120+ | ✓ | ✓ | ✓ | High |
| Firefox | 121+ | ✓ | - | - | Medium |
| Safari | 17+ | ✓ | ✓ | ✓ | High |
| Edge | 120+ | ✓ | - | - | Low |

**Priority Guide**:
- High: Must test on all marked platforms
- Medium: Test on desktop only
- Low: Test if time permits

## 4. Test Cases

### Test Case E2E-001: 基本演算機能 - ハッピーパス

**Description**: As a ユーザー, I want 四則演算を実行する機能, so that 数値計算を迅速に行えるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. When ユーザーが加算演算子を選択, the 電卓アプリ shall 2つの数値を加算し結果を表示...を実行
2. When ユーザーが減算演算子を選択, the 電卓アプリ shall 2つの数値を減算し結果を表示...を実行
3. When ユーザーが乗算演算子を選択, the 電卓アプリ shall 2つの数値を乗算し結果を表示...を実行

**Expected Results**:
- ユーザーが加算演算子を選択, the 電卓アプリ shall 2つの数値を加算し結果を表示
- ユーザーが減算演算子を選択, the 電卓アプリ shall 2つの数値を減算し結果を表示
- ユーザーが乗算演算子を選択, the 電卓アプリ shall 2つの数値を乗算し結果を表示

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-002: ユーザーインターフェース - ハッピーパス

**Description**: As a ユーザー, I want 直感的なUIで数値と演算子を入力, so that スムーズに計算を実行できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The 電卓アプリ shall 0から9までの数字ボタンを表示...を実行
2. The 電卓アプリ shall 加算、減算、乗算、除算の演算子ボタンを表示...を実行
3. The 電卓アプリ shall 計算結果を表示するディスプレイエリアを提供...を実行

**Expected Results**:
- 電卓アプリ shall 0から9までの数字ボタンを表示
- 電卓アプリ shall 加算、減算、乗算、除算の演算子ボタンを表示
- 電卓アプリ shall 計算結果を表示するディスプレイエリアを提供

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-003: 計算実行と結果表示 - ハッピーパス

**Description**: As a ユーザー, I want 正確な計算結果を即座に確認, so that 計算の信頼性を確保できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. When ユーザーがイコールボタンをクリック, the 電卓アプリ shall 入力された式を評価し結果を表示...を実行
2. The 電卓アプリ shall 複雑な式（例：2 + 3 * 4）を評価し、演算の優先順位（乗除が加減より優先）を正しく処理...を実行
3. When 計算結果が小数を含む場合, the 電卓アプリ shall 適切な精度（小数点以下8桁まで）で結果を表示...を実行

**Expected Results**:
- ユーザーがイコールボタンをクリック, the 電卓アプリ shall 入力された式を評価し結果を表示
- 電卓アプリ shall 複雑な式（例：2 + 3 * 4）を評価し、演算の優先順位（乗除が加減より優先）を正しく処理
- 計算結果が小数を含む場合, the 電卓アプリ shall 適切な精度（小数点以下8桁まで）で結果を表示

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-004: エラーハンドリング - ハッピーパス

**Description**: As a ユーザー, I want エラー時に適切なメッセージを確認, so that 問題を理解し修正できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. If ユーザーが0で除算を試みた場合, then the 電卓アプリ shall "エラー: ゼロ除算" メッセージを表示...を実行
2. If 数値がオーバーフローした場合, then the 電卓アプリ shall "エラー: 数値が範囲外" メッセージを表示...を実行
3. If 無効な入力パターンが検出された場合, then the 電卓アプリ shall 計算を実行せずエラーメッセージを表示...を実行

**Expected Results**:
- ユーザーが0で除算を試みた場合, then the 電卓アプリ shall "エラー: ゼロ除算" メッセージを表示
- 数値がオーバーフローした場合, then the 電卓アプリ shall "エラー: 数値が範囲外" メッセージを表示
- 無効な入力パターンが検出された場合, then the 電卓アプリ shall 計算を実行せずエラーメッセージを表示

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-005: エラーハンドリング - エラーフロー

**Description**: エラー発生時に適切にハンドリングされることを確認

**Preconditions**:
- アプリケーションが起動している

**Test Steps**:
1. 無効な入力でフローを開始する
2. エラーメッセージが表示されることを確認する
3. ユーザーが回復できることを確認する

**Expected Results**:
- 適切なエラーメッセージが表示される
- ユーザーが操作を継続できる
- データの整合性が保たれる

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

### Test Case E2E-006: Webアプリケーション基盤 - ハッピーパス

**Description**: As a ユーザー, I want 様々なデバイスとブラウザで電卓を使用, so that いつでもどこでも計算できるが達成できることをエンドツーエンドで確認

**Preconditions**:
- アプリケーションが起動している
- テストユーザーが作成されている
- テストデータが準備されている

**Test Steps**:
1. The 電卓アプリ shall モダンブラウザ（Chrome、Firefox、Safari、Edge）で動作...を実行
2. The 電卓アプリ shall レスポンシブデザインを実装しモバイルデバイスでも利用可能...を実行
3. The 電卓アプリ shall ページ読み込み後3秒以内に操作可能な状態...を実行

**Expected Results**:
- 電卓アプリ shall モダンブラウザ（Chrome、Firefox、Safari、Edge）で動作
- 電卓アプリ shall レスポンシブデザインを実装しモバイルデバイスでも利用可能
- 電卓アプリ shall ページ読み込み後3秒以内に操作可能な状態

**Actual Results**:
[To be filled during test execution]

**Status**: [ ] Pass / [ ] Fail / [ ] Blocked

**Notes**:

---

## 5. Test Execution Summary

| ID | Test Name | Flow | Browser | Device | Status | Date | Notes |
|----|-----------|------|---------|--------|--------|------|-------|
| E2E-001 |  |  | Chrome | Desktop 
| E2E-002 |  |  | Chrome | Desktop 
| E2E-003 |  |  | Firefox | Desktop 
| E2E-004 |  |  | Chrome | Mobile 

## 6. Defects Found

| Defect ID | Severity | Description | Browser/Device | Screenshot/Video | Status |
|-----------|----------|-------------|----------------|------------------|--------|
| | High/Medium/Low  Open/In Progress/Fixed/Closed |

## 7. Sign-off

**Tested By**: _______________
**Date**: _______________
**Approved By**: _______________
**Date**: _______________

---

## Appendix A: Test Environment Setup

### Playwright Setup

```bash
# Install Playwright
npm install -D @playwright/test

# Install browsers
npx playwright install

# Run tests
npx playwright test

# Run tests in UI mode
npx playwright test --ui

# Generate HTML report
npx playwright show-report
```

### Selenium WebDriver Setup

```bash
# Install Selenium (Node.js)
npm install selenium-webdriver

# Download browser drivers
# ChromeDriver, GeckoDriver, etc.

# Run tests
node e2e-tests/registration.test.js
```

### Cypress Setup

```bash
# Install Cypress
npm install -D cypress

# Open Cypress
npx cypress open

# Run tests headless
npx cypress run

# Run specific test
npx cypress run --spec "cypress/e2e/registration.cy.js"
```

## Appendix B: Code Examples

### Example E2E Test Code (Playwright)

```typescript
import { test, expect } from '@playwright/test';

test('User registration flow', async ({ page }) => {
  // Navigate to landing page
  await page.goto('https://staging.example.com');

  // Click sign up button
  await page.click('text=Sign Up');

  // Fill registration form
  await page.fill('input[name="name"]', 'Test User');
  await page.fill('input[name="email"]', 'test@example.com');
  await page.fill('input[name="password"]', 'Test1234!');

  // Submit form
  await page.click('button[type="submit"]');

  // Verify confirmation message
  await expect(page.locator('text=Account created successfully')).toBeVisible();

  // Verify redirect to dashboard
  await expect(page).toHaveURL(/.*dashboard/);
  await expect(page.locator('text=Welcome, Test User')).toBeVisible();
});

test('Registration with invalid email', async ({ page }) => {
  await page.goto('https://staging.example.com/signup');

  await page.fill('input[name="email"]', 'notanemail');
  await page.fill('input[name="password"]', 'Test1234!');
  await page.click('button[type="submit"]');

  // Verify error message
  await expect(page.locator('text=Please enter a valid email')).toBeVisible();
});
```

### Example E2E Test Code (Selenium WebDriver)

```javascript
const { Builder, By, until } = require('selenium-webdriver');

async function testUserRegistration() {
  let driver = await new Builder().forBrowser('chrome').build();

  try {
    // Navigate to landing page
    await driver.get('https://staging.example.com');

    // Click sign up button
    await driver.findElement(By.linkText('Sign Up')).click();

    // Fill registration form
    await driver.findElement(By.name('name')).sendKeys('Test User');
    await driver.findElement(By.name('email')).sendKeys('test@example.com');
    await driver.findElement(By.name('password')).sendKeys('Test1234!');

    // Submit form
    await driver.findElement(By.css('button[type="submit"]')).click();

    // Wait for confirmation
    await driver.wait(until.elementLocated(By.xpath('//*[contains(text(), "Account created")]')), 5000);

    // Verify redirect
    let currentUrl = await driver.getCurrentUrl();
    assert(currentUrl.includes('dashboard'));

  } finally {
    await driver.quit();
  }
}

testUserRegistration();
```

### Example E2E Test Code (Cypress)

```javascript
describe('User Registration Flow', () => {
  it('should complete registration successfully', () => {
    // Navigate to landing page
    cy.visit('https://staging.example.com');

    // Click sign up button
    cy.contains('Sign Up').click();

    // Fill registration form
    cy.get('input[name="name"]').type('Test User');
    cy.get('input[name="email"]').type('test@example.com');
    cy.get('input[name="password"]').type('Test1234!');

    // Submit form
    cy.get('button[type="submit"]').click();

    // Verify confirmation
    cy.contains('Account created successfully').should('be.visible');

    // Verify redirect to dashboard
    cy.url().should('include', '/dashboard');
    cy.contains('Welcome, Test User').should('be.visible');
  });

  it('should show error for invalid email', () => {
    cy.visit('https://staging.example.com/signup');

    cy.get('input[name="email"]').type('notanemail');
    cy.get('input[name="password"]').type('Test1234!');
    cy.get('button[type="submit"]').click();

    // Verify error message
    cy.contains('Please enter a valid email').should('be.visible');
  });
});
```

## Appendix C: Screenshot and Video Configuration

### Playwright Configuration

```typescript
// playwright.config.ts
export default {
  use: {
    screenshot: 'only-on-failure',
    video: 'retain-on-failure',
    trace: 'retain-on-failure',
  },
};
```

### Cypress Configuration

```javascript
// cypress.config.js
module.exports = {
  video: true,
  screenshotOnRunFailure: true,
  videosFolder: 'cypress/videos',
  screenshotsFolder: 'cypress/screenshots',
};
```

## Appendix D: Execution Timing

## Phase B (Before Release) - Manual Execution

E2E tests are executed manually before creating a release tag:

1. After PR is merged to main branch
2. Before creating a release tag
3. Run all E2E tests in Phase B
4. Verify all critical user flows pass before proceeding to release

E2E tests are **NOT** executed automatically in CI/CD during PR phase (only unit tests run automatically).
