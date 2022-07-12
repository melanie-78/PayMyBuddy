package com.openclassrooms.payMyBuddy.dto;

import lombok.Data;

@Data
public class TransactionCustomerDto {
    private String friend;
    private String description;
    private Double amount;
}
