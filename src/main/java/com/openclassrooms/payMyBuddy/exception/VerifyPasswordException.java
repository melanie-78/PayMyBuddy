package com.openclassrooms.payMyBuddy.exception;

public class VerifyPasswordException extends Exception{
    public VerifyPasswordException(String errorMessage) {
        super(errorMessage);
    }
}
