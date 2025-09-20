package ru.zinoviev.quest.request.handler.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.zinoviev.quest.request.handler.jpa.entity.QuestUserJPA;

import java.util.Optional;

public interface UserRepository extends JpaRepository<QuestUserJPA, Long> {

    @Query(value = "select u from QuestUserJPA u where u.telegramId = ?1")
    Optional<QuestUserJPA> findByTelegramId(Long userId);
}
