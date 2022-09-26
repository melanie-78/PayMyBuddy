package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.constants.BankTransactionType;
import com.openclassrooms.payMyBuddy.exception.LowBalanceException;

public interface BankTransactionService {
    void makeBankTransaction(String myEmail, BankTransactionType bankTransactionType, Double amount, String description, String iban) throws LowBalanceException;
}
