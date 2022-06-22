package com.openclassrooms.payMyBuddy.model;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    private String description;

    private Double amount;

    @Column(name="transaction_cost")
    private Double transactionCost;

    @Column(name="created_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    @ManyToOne(cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST})
    @JoinColumn(name = "from_customer_id")
    private Customer fromCustomer;

    @ManyToOne(cascade = {CascadeType.MERGE,
                    CascadeType.PERSIST})
    @JoinColumn(name = "to_customer_id")
    private Customer friend;
}
