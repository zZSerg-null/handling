package ru.old.domain.jpa.service.impl;

import lombok.RequiredArgsConstructor;
import ru.old.domain.enums.CreationStage;
import ru.old.domain.enums.QuestNodeType;
import old.domain.jpa.entity.QuestCreationInfoJPA;
import old.domain.jpa.entity.QuestJPA;
import old.domain.jpa.entity.QuestNodeJPA;
import old.domain.jpa.entity.QuestUserJPA;
import ru.old.domain.jpa.repository.QuestCreationRepository;
import ru.old.domain.jpa.repository.QuestNodeRepository;
import ru.old.domain.jpa.repository.QuestRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import old.domain.dto.CreationInfo;
import old.domain.dto.MenuNodeInfo;
import ru.old.domain.jpa.service.CreationInfoService;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class CreationInfoServiceImpl implements CreationInfoService {

    private final QuestCreationRepository questCreationRepository;
    private final QuestRepository questRepository;
    private final QuestNodeRepository nodeRepository;

    @Override
    public void create(QuestJPA quest, QuestUserJPA user) {
        QuestCreationInfoJPA questCreationInfo = QuestCreationInfoJPA.builder()
                .quest(quest)
                .creationStatusList(List.of(CreationStage.NEW_QUEST_AWATING_NAME_CONFIRMATION))
                .author(user)
                .build();
        questCreationRepository.save(questCreationInfo);
    }

    @Override
    public CreationInfo getInfoByUserId(Long userId) {
        Optional<QuestCreationInfoJPA> questCreationOptional = questCreationRepository.findByAuthor_Id(userId);
        if (questCreationOptional.isEmpty()) {
            return null;
        }

        QuestCreationInfoJPA questCreation = questCreationOptional.get();
        QuestNodeJPA node = questCreation.getNodeJPA();

        return CreationInfo.builder()
                .creationId(questCreation.getId())
                .creationStageList(new LinkedList<>(questCreation.getCreationStatusList()))
                .questId(questCreation.getQuest().getId())
                .questName(questCreation.getQuest().getName())
                .menuNodeInfo(getNodeInfo(node))
                .build();
    }

    private MenuNodeInfo getNodeInfo(QuestNodeJPA node) {
        if (node == null) return null;

        return MenuNodeInfo.builder()
                .nodeId(node.getId())
                .mainText(node.getMainText() != null)
                .expectedUserAnswers(nodeRepository.isNodeExpectedAnswersPresent(node.getId()))
                .expectedAnswersCount(node.getExpectedAnswersCount() != 0)
                .correctAnswersReactMessages(nodeRepository.isNodeCorrectAnswersReactMessagesPresent(node.getId()))
                .wrongAnswersReactMessages(nodeRepository.isNodeWrongAnswersReactMessagesPresent(node.getId()))
                .pollAnswers(nodeRepository.isNodePollAnswersPresent(node.getId()))
                .correctPollOptionId(nodeRepository.isNodeCorrectPollOptionPresent(node.getId()))
                .explanation(node.getExplanation() != null)
                .mediaList(nodeRepository.isNodeMedialistPresent(node.getId()))
                .geoPoint(node.getGeoPoint() != null && node.getGeoPoint().getLatitude() != null)
                .geoActiveRadius(node.getGeoPoint() != null && node.getGeoPoint().getGeoActiveRadius() != null && node.getGeoPoint().getGeoActiveRadius() > 15)
                .radiusMessage(node.getGeoPoint() != null && node.getGeoPoint().getRadiusMessage() != null)
                .build();
    }

    @Override
    public void deleteInfo(CreationInfo infoDto) {
        var info = getInfoById(infoDto.getCreationId());

        Optional<QuestJPA> questJPA = questRepository.findById(infoDto.getQuestId());
        if (questJPA.isEmpty()) {
            throw new RuntimeException("Запись о квесте не найдена, хотя должна была");
        }
        questJPA.get()
                .setIsComplete(true);

        questRepository.save(questJPA.get());
        questCreationRepository.delete(info);
    }

    @Override
    public void setNode(CreationInfo creationInfo, Long nodeId) {
        // определить заколненность ноды что бы динамически создавать менюшки о ней
        long creationId = creationInfo.getCreationId();

        var info = getInfoById(creationId);
        Optional<QuestNodeJPA> nodeOptional = nodeRepository.findById(nodeId);
        if (nodeOptional.isEmpty()) {
            throw new RuntimeException("Нет ноды. Установить невозможно");
        }
        var node = nodeOptional.get();
        var list = info.getCreationStatusList();

        CreationStage stage;
        if (node.getNodeType().equals(QuestNodeType.MESSAGE)) {
            stage = CreationStage.NODE_MENU_MESSAGE;
        } else if (node.getNodeType().equals(QuestNodeType.POLL)) {
            stage = CreationStage.NODE_MENU_POLL;
        } else if (node.getNodeType().equals(QuestNodeType.GEO)) {
            stage = CreationStage.NODE_MENU_GEO;
        } else {
            stage = CreationStage.NODE_MENU_MEDIAGROUP;
        }

        //устанавливаем статусы в ДТО и сущность БД
        creationInfo.getCreationStageList().add(stage);
        list.add(stage);


        info.setNodeJPA(node);
        questCreationRepository.save(info);
    }

    @Override
    public void deleteNode(Long creationId) {
        var info = getInfoById(creationId);
        info.setNodeJPA(null);
        questCreationRepository.save(info);
    }

    @Override
    public void updateStageList(CreationInfo creationInfo) {
        var info = getInfoById(creationInfo.getCreationId());
        info.setCreationStatusList(creationInfo.getCreationStageList());
        questCreationRepository.save(info);
    }

    private QuestCreationInfoJPA getInfoById(long id) {
        var optionalInfo = questCreationRepository.findById(id);
        if (optionalInfo.isEmpty()) {
            throw new RuntimeException("QuestCreationInfoJPA не найден, а должен был. Странно");
        }
        return optionalInfo.get();
    }

}
