package fr.adamatraore.banking.adetechbanking.service;

import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;

public interface IJwtService {
    String extractUsername(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Map<String, Object> extraClaims, UserDetails userDetails);

    String generateToken(UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);
}
