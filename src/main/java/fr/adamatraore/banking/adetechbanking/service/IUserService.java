package fr.adamatraore.banking.adetechbanking.service;


import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;

public interface IUserService {
    BankResponseDto createAccount(UserRequestDto userRequestDto);

}
