package fr.adamatraore.banking.adetechbanking.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.adamatraore.banking.adetechbanking.dto.AuthenticationRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.AuthenticationResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.RegisterRequestDto;
import fr.adamatraore.banking.adetechbanking.service.IAuthService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final IAuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody RegisterRequestDto registerRequestDto) {
        return ResponseEntity.ok(authService.register(registerRequestDto));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        return ResponseEntity.ok(authService.authenticate(authenticationRequestDto));
    }
}
