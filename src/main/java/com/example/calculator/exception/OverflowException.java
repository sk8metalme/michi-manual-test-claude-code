package com.example.calculator.exception;

/**
 * 数値オーバーフローが発生した場合の例外
 */
public class OverflowException extends RuntimeException {

    public OverflowException(String message) {
        super(message);
    }

    public OverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
