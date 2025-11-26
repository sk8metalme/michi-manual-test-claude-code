package com.example.calculator.service;

import com.example.calculator.exception.DivisionByZeroException;
import com.example.calculator.exception.InvalidExpressionException;
import com.example.calculator.exception.OverflowException;

import java.math.BigDecimal;

/**
 * 電卓の計算サービスインターフェース
 */
public interface CalculatorService {

    /**
     * 数式を評価する
     *
     * @param expression 数式（例："2 + 3 * 4"）
     * @return 計算結果（小数点以下8桁まで）
     * @throws InvalidExpressionException 構文エラーの場合
     * @throws DivisionByZeroException ゼロ除算の場合
     * @throws OverflowException 数値オーバーフローの場合
     */
    BigDecimal evaluate(String expression)
        throws InvalidExpressionException, DivisionByZeroException, OverflowException;
}
