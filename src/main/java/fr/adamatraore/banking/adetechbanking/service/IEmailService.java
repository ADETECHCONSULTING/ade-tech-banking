package fr.adamatraore.banking.adetechbanking.service;

import fr.adamatraore.banking.adetechbanking.dto.EmailDto;

public interface IEmailService {
    void sendEmailAlert(EmailDto emailDto);
}
