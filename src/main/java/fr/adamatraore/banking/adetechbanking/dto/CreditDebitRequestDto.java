package fr.adamatraore.banking.adetechbanking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreditDebitRequestDto {
    private String accountNumber;
    private BigDecimal amount;
}
