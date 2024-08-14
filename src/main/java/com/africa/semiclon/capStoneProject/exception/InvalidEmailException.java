package com.africa.semiclon.capStoneProject.exception;

public class InvalidEmailException extends RuntimeException {
   public InvalidEmailException (String message) {
       super(message);
   }
}
