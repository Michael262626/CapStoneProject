package com.africa.semiclon.capStoneProject.exception;

public class EmailFailedException extends RuntimeException{
    public EmailFailedException(String message) {
        super(message);
    }
}
