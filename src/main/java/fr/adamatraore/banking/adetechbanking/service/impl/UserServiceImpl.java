package fr.adamatraore.banking.adetechbanking.service.impl;

import java.math.BigInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adamatraore.banking.adetechbanking.dto.AccountInfoDto;
import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.CreditDebitRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.EmailDto;
import fr.adamatraore.banking.adetechbanking.dto.EnquiryRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.TransactionDto;
import fr.adamatraore.banking.adetechbanking.dto.TransferRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;
import fr.adamatraore.banking.adetechbanking.entity.User;
import fr.adamatraore.banking.adetechbanking.mapper.IUserMapper;
import fr.adamatraore.banking.adetechbanking.repository.UserRepository;
import fr.adamatraore.banking.adetechbanking.service.IEmailService;
import fr.adamatraore.banking.adetechbanking.service.ITransactionService;
import fr.adamatraore.banking.adetechbanking.service.IUserService;
import fr.adamatraore.banking.adetechbanking.utils.AccountUtils;
import fr.adamatraore.banking.adetechbanking.utils.MailUtils;

@Service
public class UserServiceImpl implements IUserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private IEmailService emailService;

        @Autowired
        private IUserMapper userMapper;

        @Autowired
        private ITransactionService transactionService;

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

                        // send email alert
                        EmailDto emailDto = EmailDto.builder()
                                        .to(savedUser.getEmail())
                                        .subject(MailUtils.ACCOUNT_CREATION_SUBJECT)
                                        .body(MailUtils.ACCOUNT_CREATION_BODY + savedUser.getAccountNumber())
                                        .build();

                        emailService.sendEmailAlert(emailDto);

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

        @Override
        public BankResponseDto balanceEnquiry(EnquiryRequestDto request) {
                // check if the provided account number exists in the db
                boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
                if (!isAccountExist) {
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_FOUND_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND)
                                        .accountInfo(null)
                                        .build();
                }

                User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
                return BankResponseDto.builder()
                                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_FOUND_SUCCESS)
                                .accountInfo(AccountInfoDto.builder()
                                                .accountBalance(foundUser.getAccountBalance())
                                                .accountNumber(request.getAccountNumber())
                                                .accountName(foundUser.getFirstName() + " " + foundUser.getLastName()
                                                                + " "
                                                                + foundUser.getOtherName())
                                                .build())
                                .build();
        }

        @Override
        public String nameEnquiry(EnquiryRequestDto request) {
                boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
                if (!isAccountExist) {
                        return AccountUtils.ACCOUNT_NOT_FOUND;
                }
                User foundUser = userRepository.findByAccountNumber(request.getAccountNumber());
                return foundUser.getFirstName() + " " + foundUser.getLastName() + " " + foundUser.getOtherName();
        }

        @Override
        public BankResponseDto creditAccount(CreditDebitRequestDto request) {
                // checking if the account exists
                boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
                if (!isAccountExist) {
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_FOUND_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND)
                                        .accountInfo(null)
                                        .build();
                }

                User userToCredit = userRepository.findByAccountNumber(request.getAccountNumber());
                userToCredit.setAccountBalance(userToCredit.getAccountBalance().add(request.getAmount()));
                userRepository.save(userToCredit);

                // save transaction
                TransactionDto transactionDto = TransactionDto.builder()
                                .sourceAccountNumber(request.getAccountNumber())
                                .amount(request.getAmount())
                                .transactionType(AccountUtils.CREDIT)
                                .build();

                transactionService.saveTransaction(transactionDto);

                return BankResponseDto.builder()
                                .responseCode(AccountUtils.ACCOUNT_CREDITED_SUCCESS)
                                .responseMessage(AccountUtils.ACCOUNT_CREDITED_SUCCESS_MESSAGE)
                                .accountInfo(AccountInfoDto.builder()
                                                .accountName(userToCredit.getFirstName() + " "
                                                                + userToCredit.getLastName() + " "
                                                                + userToCredit.getOtherName())
                                                .accountBalance(userToCredit.getAccountBalance())
                                                .accountNumber(request.getAccountNumber())
                                                .build())
                                .build();
        }

        @Override
        public BankResponseDto debitAccount(CreditDebitRequestDto request) {
                // check if the account exists
                // check if the amount you intend to withdraw is not more than the current
                // account balance
                boolean isAccountExist = userRepository.existsByAccountNumber(request.getAccountNumber());
                if (!isAccountExist) {
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_FOUND_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND)
                                        .accountInfo(null)
                                        .build();
                }

                User userToDebit = userRepository.findByAccountNumber(request.getAccountNumber());
                BigInteger availableBalance = userToDebit.getAccountBalance().toBigInteger();
                BigInteger debitAmount = request.getAmount().toBigInteger();
                if (availableBalance.intValue() < debitAmount.intValue()) {

                        // save transaction
                        TransactionDto transactionDto = TransactionDto.builder()
                                        .sourceAccountNumber(request.getAccountNumber())
                                        .amount(request.getAmount())
                                        .transactionType(AccountUtils.DEBIT)
                                        .build();

                        transactionService.saveTransaction(transactionDto);

                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                } else {
                        userToDebit.setAccountBalance(userToDebit.getAccountBalance().subtract(request.getAmount()));
                        userRepository.save(userToDebit);
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.ACCOUNT_DEBITED_SUCCESS_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_DEBITED_MESSAGE)
                                        .accountInfo(AccountInfoDto.builder()
                                                        .accountNumber(request.getAccountNumber())
                                                        .accountName(userToDebit.getFirstName() + " "
                                                                        + userToDebit.getLastName() + " "
                                                                        + userToDebit.getOtherName())
                                                        .accountBalance(userToDebit.getAccountBalance())
                                                        .build())
                                        .build();
                }
        }

        @Override
        public BankResponseDto transfer(TransferRequestDto request) {
                // get the account to debit
                // check if the amount im debiting is not more than the account balance
                // debit the account
                // credit the targeted account
                // send email alert to both parties

                boolean isSourceAccountExist = userRepository.existsByAccountNumber(request.getSourceAccountNumber());
                if (!isSourceAccountExist) {
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_FOUND_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND)
                                        .accountInfo(null)
                                        .build();
                }
                boolean isTargetAccountExist = userRepository.existsByAccountNumber(request.getTargetAccountNumber());
                if (!isTargetAccountExist) {
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.ACCOUNT_NOT_FOUND_CODE)
                                        .responseMessage(AccountUtils.ACCOUNT_NOT_FOUND)
                                        .accountInfo(null)
                                        .build();
                }

                User sourceUser = userRepository.findByAccountNumber(request.getSourceAccountNumber());
                if (request.getAmount().compareTo(sourceUser.getAccountBalance()) > 0) {
                        return BankResponseDto.builder()
                                        .responseCode(AccountUtils.INSUFFICIENT_BALANCE_CODE)
                                        .responseMessage(AccountUtils.INSUFFICIENT_BALANCE_MESSAGE)
                                        .accountInfo(null)
                                        .build();
                }

                User targetUser = userRepository.findByAccountNumber(request.getTargetAccountNumber());
                sourceUser.setAccountBalance(sourceUser.getAccountBalance().subtract(request.getAmount()));
                userRepository.save(sourceUser);

                EmailDto sourceEmailDto = EmailDto.builder()
                                .to(sourceUser.getEmail())
                                .subject(MailUtils.ACCOUNT_DEBIT_SUBJECT)
                                .body(MailUtils.ACCOUNT_DEBIT_BODY + request.getAmount())
                                .build();

                emailService.sendEmailAlert(sourceEmailDto);

                targetUser.setAccountBalance(targetUser.getAccountBalance().add(request.getAmount()));
                userRepository.save(targetUser);

                EmailDto targetEmailDto = EmailDto.builder()
                                .to(targetUser.getEmail())
                                .subject(MailUtils.ACCOUNT_CREDIT_SUBJECT)
                                .body(MailUtils.ACCOUNT_CREDIT_BODY + request.getAmount())
                                .build();

                emailService.sendEmailAlert(targetEmailDto);

                // save transaction
                TransactionDto transactionDto = TransactionDto.builder()
                                .sourceAccountNumber(request.getSourceAccountNumber())
                                .destinationAccountNumber(request.getTargetAccountNumber())
                                .amount(request.getAmount())
                                .transactionType(AccountUtils.TRANSFER)
                                .build();

                transactionService.saveTransaction(transactionDto);

                return BankResponseDto.builder()
                                .responseCode(AccountUtils.ACCOUNT_TRANSFER_SUCCESS_CODE)
                                .responseMessage(AccountUtils.ACCOUNT_TRANSFER_SUCCESS)
                                .accountInfo(null)
                                .build();
        }

}
