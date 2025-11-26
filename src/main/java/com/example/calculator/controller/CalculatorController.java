package com.example.calculator.controller;

import com.example.calculator.exception.DivisionByZeroException;
import com.example.calculator.exception.InvalidExpressionException;
import com.example.calculator.exception.OverflowException;
import com.example.calculator.model.EvaluationRequest;
import com.example.calculator.model.EvaluationResponse;
import com.example.calculator.service.CalculatorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 電卓APIのRESTコントローラー
 */
@RestController
@RequestMapping("/api/calculator")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * 数式を評価するエンドポイント
     *
     * @param request 評価リクエスト
     * @return 評価結果またはエラーレスポンス
     */
    @PostMapping("/evaluate")
    public ResponseEntity<EvaluationResponse> evaluate(@Valid @RequestBody EvaluationRequest request) {
        try {
            BigDecimal result = calculatorService.evaluate(request.getExpression());
            return ResponseEntity.ok(EvaluationResponse.success(result));

        } catch (InvalidExpressionException e) {
            // 400 Bad Request: 構文エラー
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(EvaluationResponse.error(e.getMessage()));

        } catch (DivisionByZeroException e) {
            // 422 Unprocessable Entity: ゼロ除算
            return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(EvaluationResponse.error(e.getMessage()));

        } catch (OverflowException e) {
            // 422 Unprocessable Entity: 数値オーバーフロー
            return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(EvaluationResponse.error(e.getMessage()));

        } catch (Exception e) {
            // 500 Internal Server Error: 予期しないエラー
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(EvaluationResponse.error("サーバーエラーが発生しました"));
        }
    }
}
