package com.naveen.aiplayground.exception;

import org.springframework.http.HttpStatusCode;

public class AIExceptionError extends RuntimeException{

    private final HttpStatusCode statusCode;

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

    public AIExceptionError(String message, HttpStatusCode statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
