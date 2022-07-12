package com.openclassrooms.payMyBuddy;

import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.model.Transaction;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import com.openclassrooms.payMyBuddy.repository.TransactionRepository;
import com.openclassrooms.payMyBuddy.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TestRunner implements CommandLineRunner {
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public void run(String... args) throws Exception {
        Customer savedCustomer1 = customerRepository.save(
                new Customer(null,"melanie","Adjoh","adj@gmail.com", "1234", LocalDate.of(2022,02,18),100.0, null, null, null, null));
        Customer savedCustomer2 = customerRepository.save(
                new Customer(null,"franck","Tounga","fr@gmail.com", "2345", LocalDate.of(2022,04,8),800.0,null,null,null,null));
        Customer savedCustomer3 = customerRepository.save(
                new Customer(null,"luna","Adjoh","evy@gmail.com", "3456", LocalDate.of(2022,06,16),400.0,null, null, null, null));
        Customer savedCustomer4 =customerRepository.save(
                new Customer(null,"kylian","Mbappe","ad@gmail.com", "8888", LocalDate.of(2022,02,1),200.0, null, null, null, null));


        transactionRepository.save(
                new Transaction(null, "paiement jeans", 50.0, 0.1,LocalDate.of(2022,02,18),savedCustomer1,savedCustomer2));
    }
}
