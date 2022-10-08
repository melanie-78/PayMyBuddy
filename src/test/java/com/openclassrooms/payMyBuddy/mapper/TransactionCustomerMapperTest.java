package com.openclassrooms.payMyBuddy.mapper;

import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.model.Transaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionCustomerMapperTest {
    @InjectMocks
    private TransactionCustomerMapper transactionCustomerMapper;

    @Test
    public void toDtoTest(){
        //GIVEN
        Customer friend = new Customer();
        friend.setLastName("Mel");
        Transaction transaction= new Transaction(null, "shoes", 200.0, 10.0, LocalDate.of(2022,9,7), null, friend);
        TransactionCustomerDto expected = new TransactionCustomerDto(friend.getLastName(), "shoes", 200.0);
        //WHEN
        TransactionCustomerDto actual = transactionCustomerMapper.toDto(transaction);
        //THEN
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getAmount(), actual.getAmount());
        assertEquals(expected.getFriend(), actual.getFriend());
    }
}
