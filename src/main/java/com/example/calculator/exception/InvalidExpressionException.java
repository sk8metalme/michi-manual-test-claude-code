package com.example.calculator.exception;

/**
 * 無効な数式が入力された場合の例外
 */
public class InvalidExpressionException extends RuntimeException {

    public InvalidExpressionException(String message) {
        super(message);
    }

    public InvalidExpressionException(String message, Throwable cause) {
        super(message, cause);
    }
}
