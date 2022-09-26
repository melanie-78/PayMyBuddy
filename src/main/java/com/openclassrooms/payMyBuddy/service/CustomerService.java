package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;

import java.util.List;

public interface CustomerService {
    List<BankTransactionDto> getBankTransactions(String email);
    List<TransactionCustomerDto> getTransactionToCustomers(String email);
    List<TransactionCustomerDto> getTransactionFromCustomers(String email);
}

