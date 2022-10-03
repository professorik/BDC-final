package com.example.bdc.network.exception.entities;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/** This class implements all invalid user exceptions.
 */
@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidUserException extends BadRequestException {

    /** Constructs a new invalid user exception with {@code message} as its
     * detail message.
     */
    public InvalidUserException(String message) {
        super(message);
    }

    /** Constructs a new invalid user exception with {@code "Invalid user"} as its
     * detail message.
     */
    public InvalidUserException() {
        super("Invalid user");
    }
}
