package ru.old.domain.jpa.repository;

import ru.old.domain.enums.UserRole;
import old.domain.jpa.entity.LastResponseJPA;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastResponseJPARepository extends JpaRepository<LastResponseJPA, Long> {

    Optional<LastResponseJPA> findByTelegramIdAndUserRole(Long telegramId, UserRole userRole);

}
