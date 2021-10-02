package com.project.backend.exceptions;

public class PhoneNumberIsAlreadyExistException extends RuntimeException{
    public PhoneNumberIsAlreadyExistException(String message) {
        super(message);
    }
}
