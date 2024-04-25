package fr.adamatraore.banking.adetechbanking.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TransactionDto {
    private String transactionType;
    private BigDecimal amount;
    private Timestamp transactionDate;
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private String transactionStatus;
}
