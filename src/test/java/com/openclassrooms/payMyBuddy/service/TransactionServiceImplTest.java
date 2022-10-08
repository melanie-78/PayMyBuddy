package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.exception.LowBalanceException;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import com.openclassrooms.payMyBuddy.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @InjectMocks
    private TransactionServiceImpl transactionServiceImpl;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void makeTransactionThrowsExceptionTest() throws LowBalanceException {
        //GIVEN
        String emailSender = "adj@gmail.com";
        Double amount = 100.0;
        String emailReceiver = "ad@gmail.com";
        String description = "For fun";

        Customer sender = new Customer();
        sender.setEmail(emailSender);
        sender.setBalance(50.0);

        Customer receiver = new Customer();
        receiver.setEmail(emailReceiver);

        when(customerRepository.findByEmail(emailSender)).thenReturn(Optional.of(sender));
        when(customerRepository.findByEmail(emailReceiver)).thenReturn(Optional.of(receiver));

        //WHEN
        assertThrows(LowBalanceException.class, ()->transactionServiceImpl.makeTransaction(emailSender, amount, emailReceiver,description));

        //THEN
        verify(customerRepository, Mockito.times(2)).findByEmail(any());

    }

    @Test
    public void makeTransactionTest() throws LowBalanceException {
        //GIVEN
        Double newBalance=0.0;

        String emailSender = "adj@gmail.com";
        Double amount = 100.0;
        String emailReceiver = "ad@gmail.com";
        String description = "For fun";

        Customer sender = new Customer();
        sender.setEmail(emailSender);
        sender.setBalance(200.0);

        Customer receiver = new Customer();
        receiver.setEmail(emailReceiver);
        receiver.setBalance(newBalance);

        when(customerRepository.findByEmail(emailSender)).thenReturn(Optional.of(sender));
        when(customerRepository.findByEmail(emailReceiver)).thenReturn(Optional.of(receiver));


        //WHEN
        transactionServiceImpl.makeTransaction(emailSender, amount, emailReceiver,description);

        //THEN
        assertEquals(sender.getBalance(), 99.5);
        assertEquals(receiver.getBalance(), amount);
        verify(customerRepository, Mockito.times(2)).findByEmail(any());
        verify(customerRepository, Mockito.times(2)).save(any());
        verify(transactionRepository,Mockito.times(1)).save(any());
    }
}

