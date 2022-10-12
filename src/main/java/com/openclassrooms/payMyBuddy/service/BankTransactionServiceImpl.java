package com.openclassrooms.payMyBuddy.service;

import com.openclassrooms.payMyBuddy.constants.BankTransactionType;
import com.openclassrooms.payMyBuddy.exception.LowBalanceException;
import com.openclassrooms.payMyBuddy.model.BankTransaction;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.repository.BankTransactionRepository;
import com.openclassrooms.payMyBuddy.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
public class BankTransactionServiceImpl implements BankTransactionService {
    private CustomerRepository customerRepository;
    private BankTransactionRepository bankTransactionRepository;

    public BankTransactionServiceImpl(CustomerRepository customerRepository, BankTransactionRepository bankTransactionRepository) {
        this.customerRepository = customerRepository;
        this.bankTransactionRepository = bankTransactionRepository;
    }

    @Override
    public void makeBankTransaction(String myEmail, BankTransactionType bankTransactionType, Double amount, String description, String iban) throws LowBalanceException {
        BankTransaction bankTransaction = new BankTransaction();
        Double tax = 0.0;
        Double totalAmount = 0.0;

        Customer bankTransferSender = customerRepository.findByEmail(myEmail)
                .orElseThrow(() -> new NoSuchElementException("The email sender " + myEmail + " doesn't exist in database"));
        Double balance = bankTransferSender.getBalance();
        tax = amount * 0.005;

        switch (bankTransactionType) {
            case CREDIT: {
                totalAmount = amount - tax;

                balance = balance + totalAmount;

                bankTransferSender.setBalance(balance);
                break;
            }
            case DEBIT: {
                if (balance < amount) {
                    throw new LowBalanceException("Sorry your solde, " + balance + " is not enough, you can't make this Banktransaction!!!");
                }
                totalAmount = amount + tax;

                balance = balance - totalAmount;

                bankTransferSender.setBalance(balance);
                break;
            }
            default:
                throw new IllegalArgumentException("Unkown BankTransferType");
        }

        bankTransaction.setAmount(amount);
        bankTransaction.setCustomer(bankTransferSender);
        bankTransaction.setTransactionCost(tax);
        bankTransaction.setDescription(description);
        bankTransaction.setType(bankTransactionType.name());
        bankTransaction.setIban(iban);

        bankTransactionRepository.save(bankTransaction);
    }
}

