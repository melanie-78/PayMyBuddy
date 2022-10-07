package com.openclassrooms.payMyBuddy.web.controller;

import com.openclassrooms.payMyBuddy.exception.LowBalanceException;
import com.openclassrooms.payMyBuddy.service.BankTransactionService;
import com.openclassrooms.payMyBuddy.web.ErrorResponse;
import com.openclassrooms.payMyBuddy.web.dto.BankTransactionWebDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.NoSuchElementException;

@Controller
@AllArgsConstructor
@RequestMapping("/bankTransaction")
public class BankTransactionController {
    private BankTransactionService bankTransactionService;

    @GetMapping("/formAddBankTransaction")
    public String formBankTransaction(Model model){
        model.addAttribute("bankTransactionDto", new BankTransactionWebDto());
        return "formAddBankTransaction";
    }

    @PostMapping("/saveBankTransaction")
    public String AddBankTransaction(Model model,@ModelAttribute("bankTransactionDto") BankTransactionWebDto bankTransactionDto, Principal principal){
        try{
            bankTransactionService.makeBankTransaction(principal.getName(), bankTransactionDto.getBankTransactionType(), bankTransactionDto.getAmount(), bankTransactionDto.getDescription(), bankTransactionDto.getIban());
        }catch(NoSuchElementException noSuchElementException){
            ErrorResponse errorResponse = new ErrorResponse(noSuchElementException.getMessage());
            model.addAttribute("errorResponse", errorResponse);
            return "formAddBankTransaction";
        }catch (LowBalanceException lowBalanceException){
            ErrorResponse errorResponse = new ErrorResponse(lowBalanceException.getMessage());
            model.addAttribute("errorResponse", errorResponse);
            return "formAddBankTransaction";
        }catch (IllegalArgumentException illegalArgumentException){
            return "formAddBankTransaction";
        }
        return "confirmAddBankTransaction";
    }
}

