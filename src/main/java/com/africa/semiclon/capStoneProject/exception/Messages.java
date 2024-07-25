package com.africa.semiclon.capStoneProject.exception;

public enum Messages  {
    DETAILS_ALREADY_EXISTS("User already exists");

    final String message;
    Messages(String message) {
        this.message = message;
    }
}
