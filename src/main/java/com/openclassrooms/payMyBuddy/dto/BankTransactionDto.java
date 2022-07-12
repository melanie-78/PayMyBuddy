package com.openclassrooms.payMyBuddy.dto;

import lombok.Data;

@Data
public class BankTransactionDto{
    private String iban;
    private String description;
    private Double amount;
}

