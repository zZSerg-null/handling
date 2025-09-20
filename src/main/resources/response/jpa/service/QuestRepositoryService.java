package ru.zinoviev.quest.request.handler.jpa.service;

import old.domain.jpa.entity.QuestJPA;
import old.domain.jpa.entity.QuestUserJPA;

import java.util.List;

public interface QuestRepositoryService {

    QuestJPA createNewQuest(QuestUserJPA user);

    void addQuestName(Long questId, String name);

    List<String> getUserQuestNameList(Long telegramId);
}
