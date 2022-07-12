package com.openclassrooms.payMyBuddy.mapper;

import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionCustomerMapper {

    public TransactionCustomerDto toDto (Transaction transaction){
        TransactionCustomerDto transactionDto = new TransactionCustomerDto();

        transactionDto.setFriend(transaction.getFriend().getLastName());
        transactionDto.setDescription(transaction.getDescription());
        transactionDto.setAmount(transaction.getAmount());

        return transactionDto;
    }
}
