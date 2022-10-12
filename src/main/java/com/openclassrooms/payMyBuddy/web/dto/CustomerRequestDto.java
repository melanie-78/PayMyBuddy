package com.openclassrooms.payMyBuddy.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String rePassword;
}
