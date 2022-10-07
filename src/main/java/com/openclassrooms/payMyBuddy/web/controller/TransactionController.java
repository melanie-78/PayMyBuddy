package com.openclassrooms.payMyBuddy.web.controller;

import com.openclassrooms.payMyBuddy.exception.LowBalanceException;
import com.openclassrooms.payMyBuddy.model.Customer;
import com.openclassrooms.payMyBuddy.service.CustomerService;
import com.openclassrooms.payMyBuddy.service.TransactionService;
import com.openclassrooms.payMyBuddy.web.ErrorResponse;
import com.openclassrooms.payMyBuddy.web.dto.PayRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@Slf4j
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;
    private CustomerService customerService;


    @GetMapping("/formAddTransaction")
    public String formTransaction(Model model, Principal principal){
        try{
            List<Customer> friends = customerService.getFriends(principal.getName());
            model.addAttribute("payRequestDto", new PayRequestDto());
            model.addAttribute("friends", friends );
        }catch (NoSuchElementException noSuchElementException){
            log.error(noSuchElementException.getMessage(), noSuchElementException);
        }
        return"formAddTransaction";
    }

    @PostMapping("/pay")
    public String pay(Model model, @ModelAttribute("payRequestDto") PayRequestDto payRequestDto, Principal principal){
        try{
            transactionService.makeTransaction(principal.getName(),payRequestDto.getAmount(),payRequestDto.getEmailReceiver(), payRequestDto.getDescription());
        }catch(NoSuchElementException noSuchElementException){
            log.error(noSuchElementException.getMessage(),noSuchElementException);
            ErrorResponse errorResponse = new ErrorResponse(noSuchElementException.getMessage());
            model.addAttribute("errorResponse", errorResponse);
            return "formAddTransaction";
        }catch (LowBalanceException lowBalanceException){
            log.error(lowBalanceException.getMessage(),lowBalanceException);
            ErrorResponse errorResponse = new ErrorResponse(lowBalanceException.getMessage());
            model.addAttribute("errorResponse", errorResponse);
            return "formAddTransaction";
        }
        return "confirmAddTransaction";
    }
}
