package com.openclassrooms.payMyBuddy.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionId")
    private Long transactionId;

    private String description;

    private Double amount;

    @Column(name="transaction_cost")
    private Double transactionCost;

    @Column(name="created_at")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "from_customer_id")
    private Customer fromCustomer;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "to_customer_id")
    private Customer friend;
}
