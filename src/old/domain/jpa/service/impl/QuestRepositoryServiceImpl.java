package ru.old.domain.jpa.service.impl;

import lombok.RequiredArgsConstructor;
import old.domain.jpa.entity.QuestJPA;
import old.domain.jpa.entity.QuestUserJPA;
import ru.old.domain.jpa.repository.QuestRepository;
import ru.old.domain.jpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import ru.old.domain.jpa.service.QuestRepositoryService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestRepositoryServiceImpl implements QuestRepositoryService {

    private final UserRepository userRepository;
    private final QuestRepository questRepository;

    @Override
    public QuestJPA createNewQuest(QuestUserJPA user) {
        QuestJPA questJPA = QuestJPA.builder()
                .author(user)
                .isComplete(false)
                .name("Без имени")
                .build();
        return questRepository.save(questJPA);
    }

    @Override
    public void addQuestName(Long questId, String name) {
        Optional<QuestJPA> questOptional = questRepository.findById(questId);
        if (questOptional.isEmpty()) {
            throw new RuntimeException("Квест не найден, а должен был");
        }
        QuestJPA quest = questOptional.get();
        quest.setName(name);
        questRepository.save(quest);
    }

    @Override
    public List<String> getUserQuestNameList(Long telegramId) {
        return questRepository.getUserQuestNameListByTelegramId(telegramId);
    }
}
