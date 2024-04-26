package fr.adamatraore.banking.adetechbanking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import fr.adamatraore.banking.adetechbanking.entity.AuthUser;

public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {
    Optional<AuthUser> findByEmail(String email);
}
