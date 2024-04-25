package fr.adamatraore.banking.adetechbanking.service;

import fr.adamatraore.banking.adetechbanking.dto.TransactionDto;

public interface ITransactionService {
    void saveTransaction(TransactionDto transaction);
}
