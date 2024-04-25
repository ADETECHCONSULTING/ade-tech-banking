package fr.adamatraore.banking.adetechbanking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.adamatraore.banking.adetechbanking.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {

}
