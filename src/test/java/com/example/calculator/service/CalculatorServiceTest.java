package com.example.calculator.service;

import com.example.calculator.exception.DivisionByZeroException;
import com.example.calculator.exception.InvalidExpressionException;
import com.example.calculator.parser.ExpressionParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CalculatorService のテスト")
class CalculatorServiceTest {

    @Mock
    private ExpressionParser expressionParser;

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorServiceImpl(expressionParser);
    }

    @Test
    @DisplayName("精度制御: 小数点以下8桁に丸める")
    void testPrecisionControl() {
        // 10 / 3 = 3.33333333...
        when(expressionParser.parse("10 / 3"))
            .thenReturn(new BigDecimal("3.3333333333333335"));

        BigDecimal result = calculatorService.evaluate("10 / 3");

        // 小数点以下8桁に丸められることを確認
        assertThat(result.scale()).isEqualTo(8);
        assertThat(result).isEqualByComparingTo("3.33333333");
    }

    @Test
    @DisplayName("精度制御: 整数結果も8桁の精度で返す")
    void testPrecisionControlForInteger() {
        when(expressionParser.parse("2 + 3"))
            .thenReturn(new BigDecimal("5"));

        BigDecimal result = calculatorService.evaluate("2 + 3");

        // 整数でも小数点以下8桁の形式で返す
        assertThat(result.scale()).isEqualTo(8);
        assertThat(result).isEqualByComparingTo("5.00000000");
    }

    @Test
    @DisplayName("例外変換: IllegalArgumentException → InvalidExpressionException")
    void testExceptionConversion() {
        when(expressionParser.parse("2 + + 3"))
            .thenThrow(new IllegalArgumentException("Invalid expression"));

        assertThatThrownBy(() -> calculatorService.evaluate("2 + + 3"))
            .isInstanceOf(InvalidExpressionException.class)
            .hasMessageContaining("Invalid expression");
    }

    @Test
    @DisplayName("例外変換: ArithmeticException → DivisionByZeroException")
    void testDivisionByZeroConversion() {
        when(expressionParser.parse("10 / 0"))
            .thenThrow(new ArithmeticException("Division by zero"));

        assertThatThrownBy(() -> calculatorService.evaluate("10 / 0"))
            .isInstanceOf(DivisionByZeroException.class)
            .hasMessageContaining("ゼロ除算");
    }

    @Test
    @DisplayName("正常な計算: 2 + 3 * 4 = 14")
    void testNormalCalculation() {
        when(expressionParser.parse("2 + 3 * 4"))
            .thenReturn(new BigDecimal("14"));

        BigDecimal result = calculatorService.evaluate("2 + 3 * 4");

        assertThat(result).isEqualByComparingTo("14.00000000");
        verify(expressionParser, times(1)).parse("2 + 3 * 4");
    }

    @Test
    @DisplayName("null 入力: InvalidExpressionException をスロー")
    void testNullInput() {
        assertThatThrownBy(() -> calculatorService.evaluate(null))
            .isInstanceOf(InvalidExpressionException.class);
    }

    @Test
    @DisplayName("空文字列入力: InvalidExpressionException をスロー")
    void testEmptyInput() {
        assertThatThrownBy(() -> calculatorService.evaluate(""))
            .isInstanceOf(InvalidExpressionException.class);
    }
}
