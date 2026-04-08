package ru.zinoviev.quest.request.handler.domain.db.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zinoviev.quest.request.handler.domain.db.entity.Quest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.QuestInfo;
import ru.zinoviev.quest.request.handler.domain.db.repo.QuestRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestRepositoryServiceImpl implements QuestRepositoryService {

    private final QuestRepository questRepository;

    @Override
    public List<QuestInfo> getQuestListByUserId(Long botUserId) {
        return questRepository.getQuestsByBotUserId(botUserId)
                .stream()
                .map(quest -> QuestInfo.builder()
                        .questId(quest.getId())
                        .questName(quest.getName())
                        .build())
                .toList();
    }

    @Override
    public QuestInfo findById(Long id) {
        return null;
    }

    @Override
    public void createQuest(Long botUserId, String questName) {

    }

    @Override
    public void updateQuestName(Long questId, String newName) {

    }

    @Override
    public void deleteQuest(Long id) {

    }

    @Override
    public long countQuestsByBotUser(Long botUserId) {
        return questRepository.countByBotUserId(botUserId);
    }

    @Override
    public void save(Quest quest) {

    }
}
