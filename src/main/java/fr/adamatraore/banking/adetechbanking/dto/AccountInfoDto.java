package fr.adamatraore.banking.adetechbanking.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfoDto {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
}
