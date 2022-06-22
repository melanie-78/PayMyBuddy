package com.openclassrooms.payMyBuddy.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long CustomerId;

    @Column(name="firstname")
    private String firstName;

    @Column(name="lastname")
    private String lastName;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name= "created_at")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;

    private Double balance;

    @OneToMany(mappedBy = "fromCustomer")
    private List<Transaction> fromTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "friend")
    private List<Transaction> toTransactions = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<BankTransaction> bankTransactions = new ArrayList<>();

    @ManyToMany(cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name="connection",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<Customer> friends = new ArrayList<>();
}
