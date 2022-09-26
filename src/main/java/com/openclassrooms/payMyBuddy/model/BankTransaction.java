package com.openclassrooms.payMyBuddy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "bank_transaction")
@NoArgsConstructor
@AllArgsConstructor
public class BankTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bankTransactionId")
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
