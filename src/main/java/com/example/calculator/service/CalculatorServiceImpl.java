package com.example.calculator.service;

import com.example.calculator.exception.DivisionByZeroException;
import com.example.calculator.exception.InvalidExpressionException;
import com.example.calculator.exception.OverflowException;
import com.example.calculator.parser.ExpressionParser;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 電卓の計算サービス実装クラス
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {

    private static final int PRECISION_SCALE = 8;
    private final ExpressionParser expressionParser;

    public CalculatorServiceImpl(ExpressionParser expressionParser) {
        this.expressionParser = expressionParser;
    }

    @Override
    public BigDecimal evaluate(String expression)
        throws InvalidExpressionException, DivisionByZeroException, OverflowException {

        // 入力検証
        if (expression == null || expression.trim().isEmpty()) {
            throw new InvalidExpressionException("式が空です");
        }

        try {
            // パーサーで式を評価
            BigDecimal result = expressionParser.parse(expression);

            // 精度制御: 小数点以下8桁に丸める
            return result.setScale(PRECISION_SCALE, RoundingMode.HALF_UP);

        } catch (IllegalArgumentException e) {
            // 構文エラーをビジネス例外に変換
            throw new InvalidExpressionException("無効な式: " + e.getMessage(), e);

        } catch (ArithmeticException e) {
            // ゼロ除算をビジネス例外に変換
            if (e.getMessage() != null && e.getMessage().contains("Division by zero")) {
                throw new DivisionByZeroException("エラー: ゼロ除算", e);
            }
            // その他の算術エラー
            throw new OverflowException("エラー: 数値が範囲外", e);
        }
    }
}
