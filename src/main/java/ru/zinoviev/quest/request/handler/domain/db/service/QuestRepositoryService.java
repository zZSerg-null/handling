package ru.zinoviev.quest.request.handler.domain.db.service;

import ru.zinoviev.quest.request.handler.domain.db.entity.Quest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.QuestInfo;

import java.util.List;

public interface QuestRepositoryService {

    /**
     * Найти все квесты пользователя
     */
    List<QuestInfo> getQuestListByUserId(Long botUserId);

    /**
     * Найти квест по ID
     */
    QuestInfo findById(Long id);

    /**
     * Создать квест с параметрами
     */
    void createQuest(Long botUserId, String questName);


    /**
     * Обновить имя квеста
     */
    void updateQuestName(Long questId, String newName);

    /**
     * Удалить квест по ID
     */
    void deleteQuest(Long id);

    /**
     * Получить количество квестов пользователя
     */
    long countQuestsByBotUser(Long botUserId);

    /**
     * Сохранить или обновить квест
     */
    void save(Quest quest);
}
