package com.openclassrooms.payMyBuddy.web.controller;

import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.exception.AddContactException;
import com.openclassrooms.payMyBuddy.service.CustomerService;
import com.openclassrooms.payMyBuddy.web.ErrorResponse;
import com.openclassrooms.payMyBuddy.web.dto.FriendRequestDto;
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
@RequestMapping("")
public class CustomerController {

    private CustomerService customerService;

    @GetMapping("/")
    public String home(){
        return"home";
    }

    @GetMapping("/customer/transfer")
    public String getTransactionToCustomers(Model model, Principal principal){
        try{
            List<TransactionCustomerDto> allTransactions = customerService.getTransactionFromCustomers(principal.getName());
            model.addAttribute("listTransactions", allTransactions);
        }catch(NoSuchElementException noSuchElementException){
            log.error(noSuchElementException.getMessage(),noSuchElementException );
        }
        return "transfer";
    }

    @GetMapping("/customer/formAddContact")
    public String formContact(Model model){
        model.addAttribute("friendRequestDto", new FriendRequestDto());
        return"formAddContact";
    }

    @PostMapping("/customer/save")
    public String save(Model model, @ModelAttribute("friendRequestDto") FriendRequestDto friendRequestDto, Principal principal){
        try {
            customerService.saveContact(friendRequestDto.getEmailFriend(), principal.getName());
        }catch (NoSuchElementException noSuchElementException){
            log.error(noSuchElementException.getMessage(),noSuchElementException );
            ErrorResponse errorResponse = new ErrorResponse(noSuchElementException.getMessage());
            model.addAttribute("errorResponse", errorResponse);
            return "formAddContact";
        }catch (AddContactException addContactException){
            log.error(addContactException.getMessage(),addContactException);
            ErrorResponse errorResponse = new ErrorResponse(addContactException.getMessage());
            model.addAttribute("errorResponse", errorResponse);
            return "formAddContact";
        }
        return "confirmAddContact";
    }
}