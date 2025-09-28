package ru.old.domain.jpa.service;

import old.domain.dto.CreationInfo;
import old.domain.jpa.entity.QuestJPA;
import old.domain.jpa.entity.QuestUserJPA;

public interface CreationInfoService {

    void create(QuestJPA quest, QuestUserJPA user);

    CreationInfo getInfoByUserId(Long id);

    void deleteInfo(CreationInfo infoDto);

    void updateStageList(CreationInfo creationStageList);

    void setNode(CreationInfo creationInfo, Long nodeId);

    void deleteNode(Long creationId);



}
