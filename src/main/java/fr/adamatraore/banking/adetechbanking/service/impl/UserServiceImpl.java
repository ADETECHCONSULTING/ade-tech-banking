package fr.adamatraore.banking.adetechbanking.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import fr.adamatraore.banking.adetechbanking.dto.AccountInfoDto;
import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;
import fr.adamatraore.banking.adetechbanking.entity.User;
import fr.adamatraore.banking.adetechbanking.repository.UserRepository;
import fr.adamatraore.banking.adetechbanking.service.IUserService;
import fr.adamatraore.banking.adetechbanking.utils.AccountUtils;

public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public BankResponseDto createAccount(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return BankResponseDto.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS)
                    .build();
        } else {
            User newUser = User.builder()
                    .firstName(userRequestDto.getFirstName())
                    .lastName(userRequestDto.getLastName())
                    .address(userRequestDto.getAddress())
                    .country(userRequestDto.getCountry())
                    .email(userRequestDto.getEmail())
                    .otherName(userRequestDto.getOtherName())
                    .gender(userRequestDto.getGender())
                    .phoneNumber(userRequestDto.getPhoneNumber())
                    .accountNumber(AccountUtils.generateAccountNumber())
                    .accountBalance(BigDecimal.ZERO)
                    .status(AccountUtils.AccountStatus.ACTIVE.toString())
                    .build();
            User savedUser = userRepository.save(newUser);

            return BankResponseDto.builder()
                    .responseCode(AccountUtils.SUCCESS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_CREATED_SUCCESSFULLY)
                    .accountInfo(AccountInfoDto.builder()
                            .accountBalance(savedUser.getAccountBalance())
                            .accountNumber(savedUser.getAccountNumber())
                            .accountName(savedUser.getFirstName())
                            .build())
                    .build();
        }
    }

}
