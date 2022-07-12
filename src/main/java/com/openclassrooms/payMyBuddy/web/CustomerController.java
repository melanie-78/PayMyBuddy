package com.openclassrooms.payMyBuddy.web;

import com.openclassrooms.payMyBuddy.dto.TransactionCustomerDto;
import com.openclassrooms.payMyBuddy.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("")
public class CustomerController {

    private CustomerService customerService;


    @GetMapping("/transfer")
    public String getTransactionToCustomers(Model model, @RequestParam("email") String email){
        List<TransactionCustomerDto> allTransactions = customerService.getTransactionFromCustomers(email);
        model.addAttribute("listTransactions", allTransactions);
        return "transfer";
    }

    @GetMapping("/formAddContact")
    public String formContact(Model model){
        model.addAttribute("friendRequestDto", new FriendRequestDto());
        return"formAddContact";
    }

    @PostMapping("/save")
    public String save(Model model, FriendRequestDto friendRequestDto){
       try {
           customerService.saveContact(friendRequestDto.getMyEmail(), friendRequestDto.getEmailFriend());
       }catch (RuntimeException runtimeException){
       }
       return "redirect:/formAddContact";
   }
}