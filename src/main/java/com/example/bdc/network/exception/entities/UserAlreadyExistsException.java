package com.example.bdc.network.exception.entities;

public class UserAlreadyExistsException extends InvalidUserException {

    /** Constructs a new invalid user exception with {@code "The user already exists"} as its
     * detail message.
     */
    public UserAlreadyExistsException() {
        super("The user already exists");
    }
}
