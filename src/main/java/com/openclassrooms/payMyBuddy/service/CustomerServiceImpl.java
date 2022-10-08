package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.dto.BankTransactionDto;
import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.dto.TransactionDto;
import com.openclassrooms.payMyBuddy.exception.AddContactException;
import com.openclassrooms.payMyBuddy.exception.VerifyPasswordException;
import com.openclassrooms.payMyBuddy.mapper.BankTransactionMapper;
import com.openclassrooms.payMyBuddy.mapper.TransactionCustomerMapper;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService, UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BankTransactionMapper bankTransactionMapper;
    @Autowired
    private TransactionCustomerMapper transactionMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;


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
    public List<TransactionDto> getAllTransactions(String email) {
        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("This email doesn't exist in database"));

        List<TransactionDto> list = new ArrayList<>();

        List<TransactionDto> transactionFromDtoList = byEmail.getFromTransactions().stream().map(transaction -> {
            TransactionDto transactionFromDto = transactionMapper.toTransactionDto(transaction);
            return transactionFromDto;
        }).collect(Collectors.toList());

        list.addAll(transactionFromDtoList);

        List<TransactionDto> transactionToDtoList = byEmail.getToTransactions().stream().map(transaction -> {
            TransactionDto transactionToDto = transactionMapper.toTransactionDto(transaction);
            return transactionToDto;
        }).collect(Collectors.toList());

        list.addAll(transactionToDtoList);

        List<TransactionDto> bankTransactionToDtoList = byEmail.getBankTransactions()
                .stream().map(bankTransaction -> {
                    TransactionDto bankTransactionDto = bankTransactionMapper.toTransactionDto(bankTransaction);
                    return bankTransactionDto;
                }).collect(Collectors.toList());
        list.addAll(bankTransactionToDtoList);

        return list;
    }

    @Override
    @Transactional
    public void saveContact(String emailFriend, String myEmail) throws AddContactException {

        Customer byEmail = customerRepository.findByEmail(myEmail)
                    .orElseThrow(() -> new NoSuchElementException("The email " + myEmail+ " doesn't exist in database "));

        Customer newContact = customerRepository.findByEmail(emailFriend)
                .orElseThrow(() -> new AddContactException("The email "+emailFriend+" doesn't exist in database "));

        List<Customer> friends = byEmail.getFriends();

        if (friends.contains(newContact)){
            throw new AddContactException("This person is already in your list of friends");
        }
        friends.add(newContact);
        customerRepository.saveAll(friends);
    }

    @Override
    public List<Customer> getFriends(String email) {
        Customer byEmail = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("The email "+email+" doesn't exist in database"));
        List<Customer> byEmailFriends = byEmail.getFriends();
        return byEmailFriends;
    }

    @Override
    public Customer customerRegistration(String firstName, String lastName, String email, String password, String rePassword) throws VerifyPasswordException {
        if(!password.equals(rePassword)){
            throw new VerifyPasswordException("Passwords not match");
        }else{
            String hashedPWD= passwordEncoder.encode(password);

            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setBalance(0.0);
            customer.setEmail(email);
            customer.setPassword(hashedPWD);

            Customer savedCustomer= customerRepository.save(customer);
            return savedCustomer;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()->new NoSuchElementException("The customer "+email+ " doesn't exist in database, please register"));
        User user = new User(customer.getEmail(), customer.getPassword(), new ArrayList<>());

        return user;
    }
}
