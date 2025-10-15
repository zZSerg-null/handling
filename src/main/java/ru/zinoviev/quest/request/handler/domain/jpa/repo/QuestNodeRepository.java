package ru.zinoviev.quest.request.handler.domain.jpa.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zinoviev.quest.request.handler.domain.jpa.QuestNode;

import java.util.List;

@Repository
public interface QuestNodeRepository extends JpaRepository<QuestNode, Long> {

    // Получить все ноды конкретного квеста
    List<QuestNode> findByQuestId(Long questId);

    // Или с пагинацией
    Page<QuestNode> findByQuestId(Long questId, Pageable pageable);
}
