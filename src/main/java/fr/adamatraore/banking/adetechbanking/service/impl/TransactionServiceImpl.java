package fr.adamatraore.banking.adetechbanking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.adamatraore.banking.adetechbanking.dto.TransactionDto;
import fr.adamatraore.banking.adetechbanking.entity.Transaction;
import fr.adamatraore.banking.adetechbanking.mapper.ITransactionMapper;
import fr.adamatraore.banking.adetechbanking.repository.TransactionRepository;
import fr.adamatraore.banking.adetechbanking.service.ITransactionService;

@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    ITransactionMapper transactionMapper;

    @Override
    public void saveTransaction(TransactionDto transactionDto) {
        Transaction transaction = transactionMapper.toTransaction(transactionDto);
        transactionRepository.save(transaction);
    }

}
