package fr.adamatraore.banking.adetechbanking.mapper;

import java.util.UUID;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import fr.adamatraore.banking.adetechbanking.dto.TransactionDto;
import fr.adamatraore.banking.adetechbanking.entity.Transaction;

@Mapper(componentModel = "spring", unmappedTargetPolicy = org.mapstruct.ReportingPolicy.ERROR)
public interface ITransactionMapper {

    @Mapping(target = "id", ignore = true)
    Transaction toTransaction(TransactionDto transactionDto);

    @AfterMapping
    default void afterMapping(@MappingTarget Transaction.TransactionBuilder transaction) {
        transaction.id(UUID.randomUUID().toString()).transactionStatus("SUCCESS").build();
    }
}