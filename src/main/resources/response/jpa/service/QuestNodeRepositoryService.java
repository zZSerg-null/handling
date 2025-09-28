package ru.zinoviev.quest.request.handler.jpa.service;

import old.domain.dto.CreationInfo;
import old.domain.dto.EditNodeInfo;
import old.domain.dto.request.message.BasicFile;
import old.domain.dto.request.message.Location;
import ru.old.domain.enums.CreationStage;
import old.domain.dto.UserInfo;

import java.util.List;

public interface QuestNodeRepositoryService {

    void createNode(CreationStage questNodeType, CreationInfo creationInfo);

    void addMainText(UserInfo userInfo, String text);

    void addFiles(CreationInfo creationInfo, BasicFile basicFile);

    List<String> addExpectedAnswers(CreationInfo creationInfo, String text);

    String addExpectedAnswersCount(CreationInfo creationInfo, int count);

    List<String> addCorrectAnswersReact(CreationInfo creationInfo, String text);

    List<String> addWrongMessageReact(CreationInfo creationInfo, String text);

    void addGeopoint(CreationInfo creationInfo, Location location);

    void addGeopointRadiusValue(CreationInfo creationInfo, Integer value);

    void addGeopointRadiusMessage(CreationInfo creationInfo, String text);

    void addPollAnswers(CreationInfo creationInfo, List<String> answers);

    void addPollQuestion(CreationInfo creationInfo, String text);

    void addPollExplanation(CreationInfo creationInfo, String text);

    void isNodeEmptyMainFields(CreationInfo creationInfo);

    void deleteNode(CreationInfo creationInfo);

    void setDefaultFieldData(CreationInfo creationInfo);

    List<EditNodeInfo> getNodeListByQuestId(CreationInfo questId);

    void moveNodeUp(Long nodeId);

    void moveNodeDown(Long nodeId);
}
