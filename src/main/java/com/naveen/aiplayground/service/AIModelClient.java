package com.naveen.aiplayground.service;

import com.naveen.aiplayground.exception.AIExceptionError;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

public interface AIModelClient {

    Optional<String> askClient(final String message);

    default Optional<String> sendMessage(final String message) {
        try {
            return askClient(message);
        } catch (Exception ex) {
            if (ex instanceof HttpClientErrorException.Unauthorized) {
                throw new AIExceptionError("Invalid API Key" , HttpStatus.UNAUTHORIZED);
            } else if (ex instanceof HttpClientErrorException.TooManyRequests) {
                throw new AIExceptionError("Too Many Message at the Moment, Try again later",HttpStatus.TOO_MANY_REQUESTS);
            } else if (ex instanceof HttpClientErrorException.BadRequest) {
                throw new AIExceptionError("Bad request", HttpStatus.BAD_REQUEST);
            } else {
                throw new AIExceptionError("Service is unavailable",HttpStatus.SERVICE_UNAVAILABLE);
            }
        }
    }
}
