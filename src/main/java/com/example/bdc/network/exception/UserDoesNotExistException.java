package com.example.bdc.network.exception;

public class UserDoesNotExistException extends InvalidUserException {
    public UserDoesNotExistException() {
        super("The user doesn't exist");
    }
}
