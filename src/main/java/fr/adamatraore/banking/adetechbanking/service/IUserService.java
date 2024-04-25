package fr.adamatraore.banking.adetechbanking.service;

import fr.adamatraore.banking.adetechbanking.dto.BankResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.CreditDebitRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.EnquiryRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.TransferRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.UserRequestDto;

public interface IUserService {
    BankResponseDto createAccount(UserRequestDto userRequestDto);

    BankResponseDto balanceEnquiry(EnquiryRequestDto request);

    String nameEnquiry(EnquiryRequestDto request);

    BankResponseDto creditAccount(CreditDebitRequestDto request);

    BankResponseDto debitAccount(CreditDebitRequestDto request);

    BankResponseDto transfer(TransferRequestDto request);
}
