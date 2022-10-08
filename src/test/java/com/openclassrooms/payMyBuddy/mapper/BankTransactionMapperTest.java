package com.openclassrooms.payMyBuddy.mapper;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.model.BankTransaction;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BankTransactionMapperTest {
        @InjectMocks
        private BankTransactionMapper bankTransactionMapper;

        @Test
        public void toDtoTest(){
            //GIVEN
            BankTransaction bankTransaction= new BankTransaction(null,"FR", "food", "CREDIT",1000.0,50.0, null);
            BankTransactionDto expected = new BankTransactionDto("FR", "food", 1000.0);
            //WHEN
            BankTransactionDto actual = bankTransactionMapper.toDto(bankTransaction);
            //THEN
            assertEquals(expected.getAmount(), actual.getAmount());
            assertEquals(expected.getDescription(), actual.getDescription());
            assertEquals(expected.getIban(), actual.getIban());
        }
}
