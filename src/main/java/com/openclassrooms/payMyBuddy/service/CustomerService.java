package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.mapper.BankTransactionMapper;
import com.openclassrooms.payMyBuddy.mapper.TransactionCustomerMapper;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;


@Service
public class CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankTransactionMapper bankTransactionMapper;
    @Autowired
    private TransactionCustomerMapper transactionMapper;

    public List<BankTransactionDto> getBankTransactions(String email){

        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        List<BankTransactionDto> bankTransactionDtoList = byEmail.getBankTransactions()
                .stream().map(bankTransaction -> {
                    BankTransactionDto bankTransactionDto = bankTransactionMapper.toDto(bankTransaction);
                    return bankTransactionDto;
                }).collect(Collectors.toList());
        return bankTransactionDtoList;
    }

    public List<TransactionCustomerDto> getTransactionToCustomers(String email){

        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        List<TransactionCustomerDto> transactionToDtoList = byEmail.getToTransactions()
                .stream().map(transaction -> {
                    TransactionCustomerDto transactionToDto = transactionMapper.toDto(transaction);
                    return transactionToDto;
                }).collect(Collectors.toList());
        return transactionToDtoList;
    }

    public List<TransactionCustomerDto> getTransactionFromCustomers(String email){

        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        List<TransactionCustomerDto> transactionFromDtoList = byEmail.getFromTransactions().stream().map(transaction -> {
            TransactionCustomerDto transactionFromDto = transactionMapper.toDto(transaction);
            return transactionFromDto;
        }).collect(Collectors.toList());

        return transactionFromDtoList;
    }

    @Transactional
    public void saveContact(String myEmail, String emailFriend){

        Customer byEmail = customerRepository.findByEmail(myEmail)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        Customer newContact = customerRepository.findByEmail(emailFriend)
                .orElseThrow(() -> new NoSuchElementException("This email doesn't exist in database"));

        List<Customer> friends = byEmail.getFriends();

        if (friends.contains(newContact)){
            throw new RuntimeException("This person is already in your list of friends");
        }
        friends.add(newContact);
        customerRepository.saveAll(friends);
    }

}
