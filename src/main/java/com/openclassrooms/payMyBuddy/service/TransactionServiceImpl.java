package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.exception.LowBalanceException;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.model.Transaction;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import com.openclassrooms.payMyBuddy.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService{
    private CustomerRepository customerRepository;
    private TransactionRepository transactionRepository;

    public TransactionServiceImpl(CustomerRepository customerRepository, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void makeTransaction(String emailSender, Double amount, String emailReceiver, String description) throws LowBalanceException {
        Transaction transaction = new Transaction();
        Double tax = 0.0;
        Double totalAmount = 0.0;

        Customer sender= customerRepository.findByEmail(emailSender)
                .orElseThrow(()->new NoSuchElementException("The email sender "+emailSender+" doesn't exist in database"));
        Customer receiver = customerRepository.findByEmail(emailReceiver)
                .orElseThrow(() -> new NoSuchElementException("The email receiver "+emailReceiver+" doesn't exist in database"));

        Double balance = sender.getBalance();

        if (balance<amount){
            throw new LowBalanceException("Sorry your solde, "+balance+" is not enough, you can't make this transaction!!!");
        }
        tax = amount*0.005;
        totalAmount = amount + tax;

        balance = balance - totalAmount;
        sender.setBalance(balance);

        Double newBalance = receiver.getBalance();
        newBalance= newBalance+amount;
        receiver.setBalance(newBalance);

        customerRepository.save(sender);
        customerRepository.save(receiver);

        transaction.setTransactionCost(tax);
        transaction.setAmount(amount);
        transaction.setFriend(receiver);
        transaction.setFromCustomer(sender);
        transaction.setCreatedAt(LocalDate.now());
        transaction.setDescription(description);

        transactionRepository.save(transaction);
    }
}
