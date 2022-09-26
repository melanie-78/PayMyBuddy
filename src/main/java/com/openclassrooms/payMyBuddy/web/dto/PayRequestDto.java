package com.openclassrooms.payMyBuddy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayRequestDto {
    private String emailSender;
    private Double amount;
    private String emailReceiver;
    private String description;
}
