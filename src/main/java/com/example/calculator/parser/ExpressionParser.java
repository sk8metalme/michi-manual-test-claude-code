package com.example.calculator.parser;

import java.math.BigDecimal;

/**
 * 数式をパースして評価するインターフェース
 */
public interface ExpressionParser {

    /**
     * 数式をパースして評価する
     *
     * @param expression 数式文字列（例："2 + 3 * 4"）
     * @return 評価結果
     * @throws IllegalArgumentException 構文エラーの場合
     * @throws ArithmeticException ゼロ除算などの算術エラーの場合
     */
    BigDecimal parse(String expression) throws IllegalArgumentException, ArithmeticException;
}
