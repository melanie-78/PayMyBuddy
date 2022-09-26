package com.openclassrooms.payMyBuddy.web.dto;

import com.openclassrooms.payMyBuddy.constants.BankTransactionType;
import lombok.Data;

@Data
public class BankTransactionWebDto {
    private String myEmail;
    private BankTransactionType bankTransactionType;
    private Double amount;
    private String description;
    private String iban;
}
