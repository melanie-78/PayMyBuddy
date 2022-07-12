package com.openclassrooms.payMyBuddy.mapper;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.model.BankTransaction;
import org.springframework.stereotype.Component;

@Component
public class BankTransactionMapper {

    public BankTransactionDto toDto(BankTransaction bankTransaction){
        BankTransactionDto bankTransactionDto= new BankTransactionDto();

        bankTransactionDto.setIban(bankTransaction.getIban());
        bankTransactionDto.setDescription(bankTransaction.getDescription());
        bankTransactionDto.setAmount(bankTransaction.getAmount());

        return bankTransactionDto;
    }
}
