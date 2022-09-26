package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.exception.LowBalanceException;

public interface TransactionService {
    void makeTransaction(String emailSender, Double amount, String emailReceiver, String description) throws LowBalanceException;
}
