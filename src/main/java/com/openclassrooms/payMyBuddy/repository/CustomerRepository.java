package com.openclassrooms.payMyBuddy.repository;

import com.openclassrooms.payMyBuddy.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
