package fr.adamatraore.banking.adetechbanking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDto {
    private String sourceAccountNumber;
    private String targetAccountNumber;
    private BigDecimal amount;
}
