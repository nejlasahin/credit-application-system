package com.project.backend.exceptions;

public class IdentityNumberIsAlreadyExistException extends RuntimeException{

	public IdentityNumberIsAlreadyExistException(String message) {
        super(message);
    }
}

