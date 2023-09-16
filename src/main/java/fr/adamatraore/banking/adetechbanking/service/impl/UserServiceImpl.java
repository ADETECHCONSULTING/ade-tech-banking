package fr.adamatraore.banking.adetechbanking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adamatraore.banking.adetechbanking.dto.AccountInfoDto;
import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;
import fr.adamatraore.banking.adetechbanking.entity.User;
import fr.adamatraore.banking.adetechbanking.mapper.IUserMapper;
import fr.adamatraore.banking.adetechbanking.repository.UserRepository;
import fr.adamatraore.banking.adetechbanking.service.IUserService;
import fr.adamatraore.banking.adetechbanking.utils.AccountUtils;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IUserMapper userMapper;

    @Override
    public BankResponseDto createAccount(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {
            return BankResponseDto.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXISTS_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXISTS)
                    .build();
        } else {
            User newUser = userMapper.toCreateUser(userRequestDto);
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
