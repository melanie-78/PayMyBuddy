package com.openclassrooms.payMyBuddy.mapper;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionDto;
import com.openclassrooms.payMyBuddy.model.BankTransaction;
import com.openclassrooms.payMyBuddy.model.Transaction;
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

    public TransactionDto toTransactionDto(BankTransaction bankTransaction) {
        TransactionDto transactionDto = new TransactionDto();

        transactionDto.setLabel(bankTransaction.getIban());
        transactionDto.setDescription(bankTransaction.getDescription());
        transactionDto.setAmount(bankTransaction.getAmount());

        return transactionDto;
    }
}
