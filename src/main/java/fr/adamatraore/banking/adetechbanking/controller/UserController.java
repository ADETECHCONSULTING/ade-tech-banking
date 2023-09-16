package fr.adamatraore.banking.adetechbanking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;
import fr.adamatraore.banking.adetechbanking.service.IUserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private IUserService userService;

    @PostMapping(value="/create-account")
    public BankResponseDto createAccount(@RequestBody UserRequestDto request) {        
        return userService.createAccount(request);
    }
    
}
