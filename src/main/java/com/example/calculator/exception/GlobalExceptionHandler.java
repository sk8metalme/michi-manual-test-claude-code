package com.example.calculator.exception;

import com.example.calculator.model.EvaluationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * グローバル例外ハンドラー
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(InvalidExpressionException.class)
    public ResponseEntity<EvaluationResponse> handleInvalidExpression(InvalidExpressionException ex) {
        logger.error("Invalid expression error: {}", ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(EvaluationResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(DivisionByZeroException.class)
    public ResponseEntity<EvaluationResponse> handleDivisionByZero(DivisionByZeroException ex) {
        logger.warn("Division by zero: {}", ex.getMessage());
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(EvaluationResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(OverflowException.class)
    public ResponseEntity<EvaluationResponse> handleOverflow(OverflowException ex) {
        logger.error("Overflow error: {}", ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .body(EvaluationResponse.error(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<EvaluationResponse> handleValidationError(MethodArgumentNotValidException ex) {
        logger.error("Validation error: {}", ex.getMessage());
        String errorMessage = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> error.getDefaultMessage())
            .findFirst()
            .orElse("バリデーションエラー");

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(EvaluationResponse.error(errorMessage));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EvaluationResponse> handleGeneralException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(EvaluationResponse.error("サーバーエラーが発生しました。しばらくしてから再試行してください"));
    }
}
