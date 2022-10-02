package com.example.bdc.network.exception;

public class UserAlreadyExistsException extends InvalidUserException {
    public UserAlreadyExistsException() {
        super("The user already exists");
    }
}
