package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.constants.BankTransactionType;
import com.openclassrooms.payMyBuddy.exception.LowBalanceException;
import com.openclassrooms.payMyBuddy.model.BankTransaction;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.repository.BankTransactionRepository;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BankTransactionServiceImplTest {
    @InjectMocks
    private BankTransactionServiceImpl bankTransactionServiceImpl;

    @Mock
    private BankTransactionRepository bankTransactionRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void makeBankTransactionCreditTest() throws LowBalanceException {
        //GIVEN
        String myEmail = "adjmel@gmail.com";
        Double amount = 100.0;
        String description = "for kids";
        String iban = "FR";
        Customer bankTransferSender = new Customer();
        bankTransferSender.setBalance(0.0);

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setIban(iban);
        bankTransaction.setDescription(description);
        bankTransaction.setType("CREDIT");
        bankTransaction.setAmount(amount);
        bankTransaction.setCustomer(bankTransferSender);
        bankTransaction.setTransactionCost(0.5);

        when(customerRepository.findByEmail(myEmail)).thenReturn(Optional.of(bankTransferSender));

        //WHEN
        bankTransactionServiceImpl.makeBankTransaction(myEmail, BankTransactionType.CREDIT, 100.0, "for kids", "FR" );

        //THEN
        verify(customerRepository, Mockito.times(1)).findByEmail(myEmail);
        verify(bankTransactionRepository, Mockito.times(1)).save(bankTransaction);
    }

    @Test
    public void makeBankTransactionDebitThrowsExceptionTest() throws LowBalanceException {
        //GIVEN
        String myEmail = "adjmel@gmail.com";
        Double amount = 100.0;
        String description = "for kids";
        String iban = "FR";
        Customer bankTransferSender = new Customer();
        bankTransferSender.setBalance(50.0);

        when(customerRepository.findByEmail(myEmail)).thenReturn(Optional.of(bankTransferSender));

        //WHEN
        assertThrows(LowBalanceException.class,()->bankTransactionServiceImpl.makeBankTransaction(myEmail, BankTransactionType.DEBIT, 100.0, "for kids", "FR" ));

        //THEN
        verify(customerRepository, Mockito.times(1)).findByEmail(myEmail);
    }

    @Test
    public void makeBankTransactionDebitTest() throws LowBalanceException {
        //GIVEN
        String myEmail = "adjmel@gmail.com";
        Double amount = 100.0;
        String description = "for kids";
        String iban = "FR";
        Customer bankTransferSender = new Customer();
        bankTransferSender.setBalance(500.0);

        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setIban(iban);
        bankTransaction.setDescription(description);
        bankTransaction.setType("DEBIT");
        bankTransaction.setAmount(amount);
        bankTransaction.setCustomer(bankTransferSender);
        bankTransaction.setTransactionCost(0.5);

        when(customerRepository.findByEmail(myEmail)).thenReturn(Optional.of(bankTransferSender));

        //WHEN
        bankTransactionServiceImpl.makeBankTransaction(myEmail, BankTransactionType.DEBIT, 100.0, "for kids", "FR" );

        //THEN
        verify(customerRepository, Mockito.times(1)).findByEmail(myEmail);
        verify(bankTransactionRepository, Mockito.times(1)).save(bankTransaction);
    }

    //@Test
    public void makeBankTransactionNoTypeTest() throws LowBalanceException {
        //GIVEN
        String myEmail = "adjmel@gmail.com";

        Customer bankTransferSender = new Customer();


        when(customerRepository.findByEmail(myEmail)).thenReturn(Optional.of(bankTransferSender));

        //WHEN
        assertThrows(IllegalArgumentException.class, ()->bankTransactionServiceImpl.makeBankTransaction(myEmail, BankTransactionType.CREDIT, 100.0, "for kids", "FR" ));

        //THEN
        verify(customerRepository, Mockito.times(1)).findByEmail(myEmail);
    }
}
