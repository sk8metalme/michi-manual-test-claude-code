package com.example.calculator.exception;

/**
 * ゼロ除算が発生した場合の例外
 */
public class DivisionByZeroException extends RuntimeException {

    public DivisionByZeroException(String message) {
        super(message);
    }

    public DivisionByZeroException(String message, Throwable cause) {
        super(message, cause);
    }
}
