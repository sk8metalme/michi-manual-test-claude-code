package com.example.calculator.controller;

import com.example.calculator.exception.DivisionByZeroException;
import com.example.calculator.exception.InvalidExpressionException;
import com.example.calculator.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@WebMvcTest(CalculatorController.class)
@DisplayName("CalculatorController の統合テスト")
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CalculatorService calculatorService;

    @Test
    @DisplayName("POST /api/calculator/evaluate: 正常な計算")
    void testEvaluateSuccess() throws Exception {
        when(calculatorService.evaluate("2 + 3 * 4"))
            .thenReturn(new BigDecimal("14.00000000"));

        mockMvc.perform(post("/api/calculator/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"expression\": \"2 + 3 * 4\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result", is(14.0)))
            .andExpect(jsonPath("$.error").doesNotExist());

        verify(calculatorService, times(1)).evaluate("2 + 3 * 4");
    }

    @Test
    @DisplayName("POST /api/calculator/evaluate: 400 Bad Request（構文エラー）")
    void testEvaluateSyntaxError() throws Exception {
        when(calculatorService.evaluate("2 + + 3"))
            .thenThrow(new InvalidExpressionException("無効な式"));

        mockMvc.perform(post("/api/calculator/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"expression\": \"2 + + 3\"}"))
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.error", containsString("無効な式")));
    }

    @Test
    @DisplayName("POST /api/calculator/evaluate: 422 Unprocessable Entity（ゼロ除算）")
    void testEvaluateDivisionByZero() throws Exception {
        when(calculatorService.evaluate("10 / 0"))
            .thenThrow(new DivisionByZeroException("エラー: ゼロ除算"));

        mockMvc.perform(post("/api/calculator/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"expression\": \"10 / 0\"}"))
            .andExpect(status().isUnprocessableEntity())
            .andExpect(jsonPath("$.error", is("エラー: ゼロ除算")));
    }

    @Test
    @DisplayName("POST /api/calculator/evaluate: 400 Bad Request（空の式）")
    void testEvaluateEmptyExpression() throws Exception {
        mockMvc.perform(post("/api/calculator/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"expression\": \"\"}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/calculator/evaluate: 400 Bad Request（式フィールドなし）")
    void testEvaluateMissingExpression() throws Exception {
        mockMvc.perform(post("/api/calculator/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST /api/calculator/evaluate: 小数点結果")
    void testEvaluateDecimalResult() throws Exception {
        when(calculatorService.evaluate("10 / 3"))
            .thenReturn(new BigDecimal("3.33333333"));

        mockMvc.perform(post("/api/calculator/evaluate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"expression\": \"10 / 3\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.result").value(closeTo(3.33333333, 0.00000001)));
    }
}
