package fr.adamatraore.banking.adetechbanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.CreditDebitRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.EnquiryRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;
import fr.adamatraore.banking.adetechbanking.service.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value = "/create-account")
    public BankResponseDto createAccount(@RequestBody UserRequestDto request) {
        return userService.createAccount(request);
    }

    @GetMapping("balanceEnquiry")
    public BankResponseDto balanceEnquiry(@RequestBody EnquiryRequestDto request) {
        return userService.balanceEnquiry(request);
    }

    @GetMapping("nameEnquiry")
    public String nameEnquiry(@RequestBody EnquiryRequestDto request) {
        return userService.nameEnquiry(request);
    }

    @PostMapping("credit")
    public BankResponseDto creditAccount(@RequestBody CreditDebitRequestDto request) {
        return userService.creditAccount(request);
    }

    @PostMapping("debit")
    public BankResponseDto debitAccount(@RequestBody CreditDebitRequestDto request) {
        return userService.debitAccount(request);
    }
}
