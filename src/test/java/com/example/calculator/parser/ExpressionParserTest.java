package com.example.calculator.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.*;

@DisplayName("ExpressionParser のテスト")
class ExpressionParserTest {

    private ExpressionParser parser;

    @BeforeEach
    void setUp() {
        parser = new Exp4jExpressionParser();
    }

    @Test
    @DisplayName("加算: 2 + 3 = 5")
    void testAddition() {
        BigDecimal result = parser.parse("2 + 3");
        assertThat(result).isEqualByComparingTo("5");
    }

    @Test
    @DisplayName("減算: 10 - 4 = 6")
    void testSubtraction() {
        BigDecimal result = parser.parse("10 - 4");
        assertThat(result).isEqualByComparingTo("6");
    }

    @Test
    @DisplayName("乗算: 3 * 4 = 12")
    void testMultiplication() {
        BigDecimal result = parser.parse("3 * 4");
        assertThat(result).isEqualByComparingTo("12");
    }

    @Test
    @DisplayName("除算: 15 / 3 = 5")
    void testDivision() {
        BigDecimal result = parser.parse("15 / 3");
        assertThat(result).isEqualByComparingTo("5");
    }

    @Test
    @DisplayName("演算優先順位: 2 + 3 * 4 = 14（乗算が先）")
    void testOperatorPrecedence() {
        BigDecimal result = parser.parse("2 + 3 * 4");
        assertThat(result).isEqualByComparingTo("14");
    }

    @Test
    @DisplayName("演算優先順位: 10 - 2 * 3 = 4（乗算が先）")
    void testOperatorPrecedenceSubtraction() {
        BigDecimal result = parser.parse("10 - 2 * 3");
        assertThat(result).isEqualByComparingTo("4");
    }

    @Test
    @DisplayName("小数点数の計算: 1.5 * 2 = 3.0")
    void testDecimalCalculation() {
        BigDecimal result = parser.parse("1.5 * 2");
        assertThat(result).isEqualByComparingTo("3.0");
    }

    @Test
    @DisplayName("小数点数の加算: 0.1 + 0.2（浮動小数点精度考慮）")
    void testDecimalAddition() {
        BigDecimal result = parser.parse("0.1 + 0.2");
        // 浮動小数点数の精度問題を考慮し、小数点以下6桁で比較
        assertThat(result.doubleValue()).isCloseTo(0.3, within(0.000001));
    }

    @Test
    @DisplayName("複雑な式: 2 + 3 * 4 - 5 / 5 = 13")
    void testComplexExpression() {
        BigDecimal result = parser.parse("2 + 3 * 4 - 5 / 5");
        assertThat(result).isEqualByComparingTo("13");
    }

    @Test
    @DisplayName("ゼロ除算: 10 / 0 で ArithmeticException をスロー")
    void testDivisionByZero() {
        assertThatThrownBy(() -> parser.parse("10 / 0"))
            .isInstanceOf(ArithmeticException.class);
    }

    @Test
    @DisplayName("無効な式: '2 + * 3' で IllegalArgumentException をスロー")
    void testInvalidOperatorSequence() {
        // exp4jは単項プラス（+）をサポートするため、'2 + + 3'は有効
        // 代わりに、明らかに無効な演算子の組み合わせをテスト
        assertThatThrownBy(() -> parser.parse("2 + * 3"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("構文エラー: 式が演算子で始まる '* 5' で IllegalArgumentException をスロー")
    void testExpressionStartsWithOperator() {
        assertThatThrownBy(() -> parser.parse("* 5"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("空の式: 空文字列で IllegalArgumentException をスロー")
    void testEmptyExpression() {
        assertThatThrownBy(() -> parser.parse(""))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("null の式: null で IllegalArgumentException をスロー")
    void testNullExpression() {
        assertThatThrownBy(() -> parser.parse(null))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
