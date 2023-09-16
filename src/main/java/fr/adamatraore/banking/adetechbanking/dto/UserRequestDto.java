package fr.adamatraore.banking.adetechbanking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {
    private String firstName;
    private String lastName;
    private String otherName;
    private String gender;
    private String address;
    private String country;
    private String email;
    private String phoneNumber;
    private String status;
}
