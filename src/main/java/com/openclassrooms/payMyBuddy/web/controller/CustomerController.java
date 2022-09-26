package com.openclassrooms.payMyBuddy.web.controller;

import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/customer/transfer")
    public String getTransactionToCustomers(Model model, @RequestParam String email){
        try{
            List<TransactionCustomerDto> allTransactions = customerService.getTransactionFromCustomers(email);
            model.addAttribute("listTransactions", allTransactions);
        }catch(NoSuchElementException noSuchElementException){
            log.error(noSuchElementException.getMessage(),noSuchElementException );
        }
        return "transfer";
    }
}