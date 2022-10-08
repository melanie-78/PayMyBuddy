package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.dto.TransactionDto;
import com.openclassrooms.payMyBuddy.exception.AddContactException;
import com.openclassrooms.payMyBuddy.exception.VerifyPasswordException;
import com.openclassrooms.payMyBuddy.model.Customer;

import java.util.List;

public interface CustomerService {
    List<BankTransactionDto> getBankTransactions(String email);
    List<TransactionCustomerDto> getTransactionToCustomers(String email);
    List<TransactionCustomerDto> getTransactionFromCustomers(String email);
    //to display the history of all transactions
    List<TransactionDto> getAllTransactions(String email);
    //for save contact in our list of contact
    void saveContact(String emailFriend, String myEmail) throws AddContactException;
    List<Customer> getFriends(String email);

    Customer customerRegistration(String firstName, String lastName, String email, String password, String rePassword) throws VerifyPasswordException;
}

