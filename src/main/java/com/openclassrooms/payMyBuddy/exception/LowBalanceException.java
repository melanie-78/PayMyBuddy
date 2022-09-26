package com.openclassrooms.payMyBuddy.exception;

public class LowBalanceException extends Exception {
    public LowBalanceException(String errorMessage){
        super(errorMessage);
    }
}
