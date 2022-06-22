package com.openclassrooms.payMyBuddy.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bank_transaction")
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long bankTransactionId;

    private String iban;

    private String description;

    private String type;

    private Double amount;

    @Column(name="transaction_cost")
    private Double transactionCost;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
