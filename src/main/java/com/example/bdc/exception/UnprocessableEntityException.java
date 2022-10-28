package com.example.bdc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This class implements all invalid image exceptions.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class UnprocessableEntityException extends BadRequestException {

    private String details;

    /** Constructs a new invalid image exception with {@code message} as its
     * detail message.
     */
    public UnprocessableEntityException(String message, String details) {
        super(message);
        this.details = details;
    }

    public String getDetails() {
        return details;
    }
}
