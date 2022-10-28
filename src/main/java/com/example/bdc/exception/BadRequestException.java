package com.example.bdc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    /** Constructs a new bad request exception with {@code message} as its
     * detail message.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
