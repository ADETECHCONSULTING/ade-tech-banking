package fr.adamatraore.banking.adetechbanking.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import fr.adamatraore.banking.adetechbanking.dto.AuthenticationRequestDto;
import fr.adamatraore.banking.adetechbanking.dto.AuthenticationResponseDto;
import fr.adamatraore.banking.adetechbanking.dto.RegisterRequestDto;
import fr.adamatraore.banking.adetechbanking.entity.AuthUser;
import fr.adamatraore.banking.adetechbanking.entity.User;
import fr.adamatraore.banking.adetechbanking.repository.AuthUserRepository;
import fr.adamatraore.banking.adetechbanking.repository.UserRepository;
import fr.adamatraore.banking.adetechbanking.service.IAuthService;
import fr.adamatraore.banking.adetechbanking.service.IJwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {

        private final AuthUserRepository authUserRepository;
        private final UserRepository userRepository;
        private final IJwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        @Override

        public AuthenticationResponseDto register(RegisterRequestDto registerRequestDto) {
                // save a new auth user then save a new user with the auth user id
                AuthUser authUser = AuthUser.builder()
                                .email(registerRequestDto.getEmail())
                                .password(passwordEncoder.encode(registerRequestDto.getPassword()))
                                .build();
                authUser = authUserRepository.save(authUser);

                userRepository.save(User.builder()
                                .authUserId(authUser.getId())
                                .firstName(registerRequestDto.getFirstname())
                                .lastName(registerRequestDto.getLastname())
                                .build());
                String token = jwtService.generateToken(authUser);
                return AuthenticationResponseDto.builder().token(token).build();
        }

        @Override
        public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getEmail(),
                                                authenticationRequestDto.getPassword()));

                AuthUser authUser = authUserRepository.findFirstByEmail(authenticationRequestDto.getEmail())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                String token = jwtService.generateToken(authUser);

                return AuthenticationResponseDto.builder().token(token).build();
        }

}
