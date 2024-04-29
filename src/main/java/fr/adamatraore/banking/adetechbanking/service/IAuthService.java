package fr.adamatraore.banking.adetechbanking.service;

import fr.adamatraore.banking.adetechbanking.dto.AuthenticationRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.AuthenticationResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.RegisterRequestDto;

public interface IAuthService {
    AuthenticationResponseDto register(RegisterRequestDto registerRequestDto);

    AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
}
