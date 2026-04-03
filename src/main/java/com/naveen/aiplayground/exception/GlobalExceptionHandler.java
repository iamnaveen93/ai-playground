package com.naveen.aiplayground.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AIExceptionError.class)
    public ResponseEntity<AIExceptionErrorMessage> handleAIExceptionError(AIExceptionError error) {
        return ResponseEntity.status(error.getStatusCode()).body(new AIExceptionErrorMessage(error.getMessage(), error.getStatusCode()));
    }
}
