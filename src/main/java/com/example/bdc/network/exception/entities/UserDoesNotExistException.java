package com.example.bdc.network.exception.entities;

public class UserDoesNotExistException extends InvalidUserException {

    /** Constructs a new invalid user exception with {@code "The user doesn't exist"} as its
     * detail message.
     */
    public UserDoesNotExistException() {
        super("The user doesn't exist");
    }
}
