package ru.zinoviev.quest.request.handler.domain.jpa.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zinoviev.quest.request.handler.domain.jpa.Quest;

import java.util.List;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {

    List<Quest> getQuestsByBotUserId(Long botUserId);

    // Количество квестов пользователя
    long countByBotUserId(Long botUserId);
}
