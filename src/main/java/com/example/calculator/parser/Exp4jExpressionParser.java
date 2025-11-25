package com.example.calculator.parser;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * exp4jライブラリを使用した数式パーサーの実装
 */
@Component
public class Exp4jExpressionParser implements ExpressionParser {

    @Override
    public BigDecimal parse(String expression) throws IllegalArgumentException, ArithmeticException {
        // null と空文字列のチェック
        if (expression == null || expression.trim().isEmpty()) {
            throw new IllegalArgumentException("Expression cannot be null or empty");
        }

        try {
            // exp4jで式を構築して評価
            Expression exp = new ExpressionBuilder(expression).build();
            double result = exp.evaluate();

            // Infinityやゼロ除算の検出
            if (Double.isInfinite(result)) {
                throw new ArithmeticException("Division by zero");
            }

            // NaNの検出（無効な演算結果）
            if (Double.isNaN(result)) {
                throw new ArithmeticException("Invalid arithmetic operation");
            }

            // BigDecimalに変換して返す
            return BigDecimal.valueOf(result);

        } catch (IllegalArgumentException e) {
            // exp4jの構文エラーをそのままスロー
            throw new IllegalArgumentException("Invalid expression: " + e.getMessage(), e);
        } catch (ArithmeticException e) {
            // 算術エラーをそのままスロー
            throw e;
        }
    }
}
