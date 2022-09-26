package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.exception.AddContactException;
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
public class CustomerServiceImpl implements CustomerService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankTransactionMapper bankTransactionMapper;
    @Autowired
    private TransactionCustomerMapper transactionMapper;


    @Override
    public List<BankTransactionDto> getBankTransactions(String email) {
        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        List<BankTransactionDto> bankTransactionDtoList = byEmail.getBankTransactions()
                .stream().map(bankTransaction -> {
                    BankTransactionDto bankTransactionDto = bankTransactionMapper.toDto(bankTransaction);
                    return bankTransactionDto;
                }).collect(Collectors.toList());
        return bankTransactionDtoList;
    }

    @Override
    public List<TransactionCustomerDto> getTransactionToCustomers(String email) {
        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("The email "+email+"doesn't exist in database"));

        List<TransactionCustomerDto> transactionToDtoList = byEmail.getToTransactions()
                .stream().map(transaction -> {
                    TransactionCustomerDto transactionToDto = transactionMapper.toDto(transaction);
                    return transactionToDto;
                }).collect(Collectors.toList());
        return transactionToDtoList;
    }

    @Override
    public List<TransactionCustomerDto> getTransactionFromCustomers(String email) {
        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        List<TransactionCustomerDto> transactionFromDtoList = byEmail.getFromTransactions().stream().map(transaction -> {
            TransactionCustomerDto transactionFromDto = transactionMapper.toDto(transaction);
            return transactionFromDto;
        }).collect(Collectors.toList());

        return transactionFromDtoList;
    }

    @Override
    @Transactional
    public void saveContact(String myEmail, String emailFriend) throws AddContactException {

        Customer byEmail = customerRepository.findByEmail(myEmail)
                .orElseThrow(()->new NoSuchElementException("The email "+myEmail+" doesn't exist in database "));

        Customer newContact = customerRepository.findByEmail(emailFriend)
                .orElseThrow(() -> new AddContactException("The email "+emailFriend+" doesn't exist in database "));

        List<Customer> friends = byEmail.getFriends();

        if (friends.contains(newContact)){
            throw new AddContactException("This person is already in your list of friends");
        }
        friends.add(newContact);
        customerRepository.saveAll(friends);
    }
}
