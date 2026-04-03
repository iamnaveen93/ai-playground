package com.naveen.aiplayground.exception;

import org.springframework.http.HttpStatusCode;

public class AIExceptionErrorMessage {

    private final String errorMessage;
    private final HttpStatusCode HttpStatus;

    public AIExceptionErrorMessage(String errorMessage, HttpStatusCode httpStatus) {
        this.errorMessage = errorMessage;
        HttpStatus = httpStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatusCode getHttpStatus() {
        return HttpStatus;
    }
}
