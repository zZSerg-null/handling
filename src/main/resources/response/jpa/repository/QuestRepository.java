package ru.zinoviev.quest.request.handler.jpa.repository;

import old.domain.jpa.entity.QuestJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestRepository extends JpaRepository<QuestJPA, Long> {

    @Query("""
            select count(q) from QuestJPA q
            join q.author a
            where a.telegramId = ?1 and q.isComplete=true
            """)
    Integer getUserQuestCount(Long userId);

    @Query("""
        select lower(q.name) from QuestJPA q
        join q.author
        where q.author.telegramId =?1 and q.isComplete
            """)
    List<String> getUserQuestNameListByTelegramId(Long telegramId);
}
