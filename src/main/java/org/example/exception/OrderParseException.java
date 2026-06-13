package org.example.exception;

public class OrderParseException extends RuntimeException {

    public OrderParseException(String message, Throwable cause) {
        super(message, cause);
    }
}