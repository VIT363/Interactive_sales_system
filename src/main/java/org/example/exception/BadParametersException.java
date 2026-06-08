package org.example.exception;

public class BadParametersException extends RuntimeException {

    public BadParametersException(String message) {
        super(message);
    }
}