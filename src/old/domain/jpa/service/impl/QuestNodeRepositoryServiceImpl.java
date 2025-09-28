package ru.old.domain.jpa.service.impl;

import lombok.RequiredArgsConstructor;
import old.domain.dto.CreationInfo;
import old.domain.dto.EditNodeInfo;
import old.domain.dto.request.FileType;
import old.domain.dto.request.message.BasicFile;
import old.domain.dto.request.message.Location;
import ru.old.domain.enums.CreationStage;
import ru.old.domain.enums.QuestNodeType;
import old.domain.jpa.entity.FileJPA;
import old.domain.jpa.entity.GeoPointJPA;
import old.domain.jpa.entity.QuestJPA;
import old.domain.jpa.entity.QuestNodeJPA;
import ru.old.domain.jpa.repository.QuestCreationRepository;
import ru.old.domain.jpa.repository.QuestNodeRepository;
import ru.old.domain.jpa.repository.QuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import old.domain.dto.BasicFileInfo;
import old.domain.dto.UserInfo;
import ru.old.domain.jpa.service.QuestNodeRepositoryService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestNodeRepositoryServiceImpl implements QuestNodeRepositoryService {

    private final QuestNodeRepository nodeRepository;
    private final QuestRepository questRepository;
    private final QuestCreationRepository creationRepository;

    @Override
    public void createNode(CreationStage stage, CreationInfo creationInfo) {
        var questOptional = questRepository.findById(creationInfo.getQuestId());
        if (questOptional.isEmpty()) {
            throw new RuntimeException("Квеста почему-то нету, но это странно");
        }

        QuestJPA quest = questOptional.get();
        QuestNodeJPA node = new QuestNodeJPA();
        node.setQuestId(quest);

        //определяем тип ноды
        QuestNodeType nodeType = null;
        switch (stage) {
            case CreationStage.NODE_MENU_MESSAGE -> {
                nodeType = QuestNodeType.MESSAGE;
                node.setGeoPoint(null);
            }
            case CreationStage.NODE_MENU_POLL -> {
                nodeType = QuestNodeType.POLL;
                node.setGeoPoint(null);
            }
            case CreationStage.NODE_MENU_GEO -> {
                nodeType = QuestNodeType.GEO;
                node.setGeoPoint(GeoPointJPA.builder().build());
            }
            case CreationStage.NODE_MENU_MEDIAGROUP -> {
                nodeType = QuestNodeType.MEDIA;
                node.setGeoPoint(null);
            }
        }
        //устанавливам тип
        node.setNodeType(nodeType);
        nodeRepository.save(node);

        //обновляем данные в таблице создания квеста
        var infoOptional = creationRepository.findById(creationInfo.getCreationId());
        if (infoOptional.isEmpty()) {
            throw new RuntimeException("Инфы не существует");
        }
        var info = infoOptional.get();
        var oldNode = info.getNodeJPA();
        if (oldNode != null) {
            oldNode.setNextNode(node);
            node.setPrevNode(oldNode);
            nodeRepository.save(oldNode);
        }

        info.setNodeJPA(node);
        creationRepository.save(info);

        if (quest.getFirstNode() == null) {
            quest.setFirstNode(node);
            questRepository.save(quest);
        }
    }

    @Override
    public void addMainText(UserInfo userInfo, String text) {
        var creationInfo = userInfo.getCreationInfo();
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.setMainText(text);

        nodeRepository.save(node);
    }

    @Override
    public void addFiles(CreationInfo creationInfo, BasicFile basicFile) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        var list = node.getMediaList();

        // Если это тип сообщения - простое сообщение с одним файлом
        if (list.size() == 1 && node.getNodeType().equals(QuestNodeType.MESSAGE)) {
            list.clear();
        }

        var newFile = getFile(basicFile);
        newFile.setNode(node);
        list.add(newFile);

        nodeRepository.save(node);
    }

    @Override
    public List<String> addExpectedAnswers(CreationInfo creationInfo, String text) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        var answers = Arrays.stream(text.split(";")).toList();

        node.getExpectedUserAnswers().addAll(answers);
        nodeRepository.save(node);

        return answers;
    }

    @Override
    public String addExpectedAnswersCount(CreationInfo creationInfo, int count) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        var expectedAnswers = node.getExpectedUserAnswers();

        if (expectedAnswers.isEmpty()) {
            return "Для установки значения количества ожидаемых ответов сначала укажите сами ответы.";
        } else if (expectedAnswers.size() < count) {
            return "Значение количества ожидаемых ответов (" + count + ") превышает их фактическое количество: " + expectedAnswers.size();
        }

        node.setExpectedAnswersCount(count);
        nodeRepository.save(node);
        return "Установлено ожидаемых ответов: " + count;
    }

    @Override
    public List<String> addCorrectAnswersReact(CreationInfo creationInfo, String text) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());

        List<String> correctAnswersReactList = Arrays.stream(text.split(";")).toList();
        node.getCorrectAnswersReactMessages().addAll(correctAnswersReactList);
        nodeRepository.save(node);

        return node.getCorrectAnswersReactMessages();
    }

    @Override
    public List<String> addWrongMessageReact(CreationInfo creationInfo, String text) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());

        List<String> wrongAnswersReactList = Arrays.stream(text.split(";")).toList();
        node.getWrongAnswersReactMessages().addAll(wrongAnswersReactList);
        nodeRepository.save(node);

        return node.getWrongAnswersReactMessages();
    }

    @Override
    public void addGeopoint(CreationInfo creationInfo, Location location) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.getGeoPoint().setLatitude(location.latitude());
        node.getGeoPoint().setLongitude(location.longitude());
        nodeRepository.save(node);
    }

    @Override
    public void addGeopointRadiusValue(CreationInfo creationInfo, Integer value) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.getGeoPoint().setGeoActiveRadius(value);
        nodeRepository.save(node);
    }

    @Override
    public void addGeopointRadiusMessage(CreationInfo creationInfo, String text) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.getGeoPoint().setRadiusMessage(text);
        nodeRepository.save(node);
    }

    @Override
    public void addPollAnswers(CreationInfo creationInfo, List<String> answers) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.setPollAnswers(IntStream.range(0, answers.size())
                .boxed()
                .collect(Collectors.toMap(r -> r, answers::get)));
        nodeRepository.save(node);
    }

    @Override
    public void addPollQuestion(CreationInfo creationInfo, String text) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.setMainText(text);
        nodeRepository.save(node);
    }

    @Override
    public void addPollExplanation(CreationInfo creationInfo, String text) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        node.setExplanation(text);
        nodeRepository.save(node);
    }

    @Override
    public void isNodeEmptyMainFields(CreationInfo creationInfo) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        if (isNodeMainFieldsNotEmpty(node)) {
            node.setMainFieldIsEmpty(true);
        }

        nodeRepository.save(node);
    }

    @Override
    public void deleteNode(CreationInfo creationInfo) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());
        Optional<QuestJPA> questJPA = questRepository.findById(creationInfo.getQuestId());
        if (questJPA.isPresent()) {
            QuestJPA questJPA1 = questJPA.get();
            if (questJPA1.getFirstNode().getId().equals(node.getId())) {
                questJPA1.setFirstNode(null);
                questRepository.save(questJPA.get());
            }
        }

        //обновляем данные в таблице создания квеста
        var infoOptional = creationRepository.findById(creationInfo.getCreationId());
        if (infoOptional.isEmpty()) {
            throw new RuntimeException("Инфы чет нету");
        }
        var info = infoOptional.get();
        info.setNodeJPA(null);
        info.setCreationStatusList(creationInfo.getCreationStageList());
        creationRepository.save(info);


        nodeRepository.delete(node);
    }

    @Override
    public void setDefaultFieldData(CreationInfo creationInfo) {
        var node = getNode(creationInfo.getMenuNodeInfo().getNodeId());

        var stage = creationInfo.getCreationStageList().peekLast();
        if (stage == null) {
            throw new RuntimeException("Что то пошло не так и стэйжа нет");
        }

        switch (stage) {
            case CreationStage.ADDING_TEXT, CreationStage.ADDING_POLL_NODE_QUESTION -> node.setMainText(null);
            case CreationStage.ADDING_FILE, CreationStage.ADDING_MEDIA_GROUP -> node.getMediaList().clear();

            case CreationStage.ADDING_GEOPOINT -> node.setGeoPoint(null);
            case CreationStage.ADDING_GEOPOINT_RADIUS_VALUE -> node.getGeoPoint().setGeoActiveRadius(0);
            case CreationStage.ADDING_GEOPOINT_RADIUS_MESSAGES -> node.getGeoPoint().setRadiusMessage(null);

            case CreationStage.ADDING_POLL_NODE_ANSWERS -> node.getPollAnswers().clear();
            case CreationStage.ADDING_POLL_NODE_EXPLANATION -> node.setExplanation(null);

            case CreationStage.ADDING_EXPECTED_ANSWERS -> node.getExpectedUserAnswers().clear();
            case CreationStage.ADDING_CORRECT_MESSAGE_REACT -> node.getCorrectAnswersReactMessages().clear();
            case CreationStage.ADDING_EXPECTED_ANSWERS_COUNT -> node.setExpectedAnswersCount(0);
            case CreationStage.ADDING_WRONG_MESSAGE_REACT -> node.getWrongAnswersReactMessages().clear();
        }
        nodeRepository.save(node);
    }

    @Override
    public List<EditNodeInfo> getNodeListByQuestId(CreationInfo creationInfo) {
        long questId = creationInfo.getQuestId();
        List<QuestNodeJPA> nodeList = nodeRepository.findByQuestId(questId);

        return nodeList.stream()
                .map(this::getNodeInfo)
                .collect(Collectors.toList());
    }

    @Override
    public void moveNodeUp(Long nodeId) {
        var current = getNode(nodeId);
        var prev = current.getPrevNode();
        if (prev == null) return;
        var next = current.getNextNode();

        current.setNextNode(prev);
        current.setPrevNode(prev.getPrevNode());

        prev.setNextNode(next);
        prev.setPrevNode(current);

        if (next != null){
            next.setPrevNode(prev);
        }

        nodeRepository.saveAll(
                Stream.of(current, prev, next)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }

    @Override
    public void moveNodeDown(Long nodeId) {
        var current = getNode(nodeId);
        var next = current.getNextNode();
        if (next == null) return;
        var prev = current.getPrevNode();

        current.setPrevNode(next);
        current.setNextNode(next.getNextNode());

        next.setPrevNode(prev);
        next.setPrevNode(current);

        if (prev != null){
            prev.setNextNode(next);
        }

        nodeRepository.saveAll(
                Stream.of(current, prev, next)
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }

    private void changeLink(QuestNodeJPA node, QuestNodeJPA prevNode, QuestNodeJPA nextNode) {
        prevNode.setNextNode(nextNode);
        node.setNextNode(prevNode);
        node.setPrevNode(prevNode.getPrevNode());
        prevNode.setPrevNode(node);
        nextNode.setPrevNode(prevNode);
    }

    private EditNodeInfo getNodeInfo(QuestNodeJPA nodeJPA) {
        return EditNodeInfo.builder()
                .nodeId(nodeJPA.getId())
                .type(nodeJPA.getNodeType())
                .mainText(getSubstring(nodeJPA.getMainText()))
                .mediaList(getMediaList(nodeJPA.getMediaList()))
                .geoPointPresent(nodeJPA.getGeoPoint() != null && nodeJPA.getGeoPoint().getLatitude() != null)
                .pollAnswersPresent(nodeJPA.getPollAnswers() != null)
                .correctPollOptionIdPresent(nodeJPA.getCorrectPollOptionId() != null)
                .build();
    }

    private List<BasicFileInfo> getMediaList(List<FileJPA> mediaList) {
        return mediaList.stream()
                .map(f -> BasicFileInfo.builder()
                        .fileType(f.getFileType())
                        .fileName(f.getFileName())
                        .fileSize(f.getFileSize())
                        .mimeType(f.getMimeType())
                        .build())
                .limit(3)
                .collect(Collectors.toList());
    }

    private String getSubstring(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        if (text.length() < 45) {
            return text;
        }
        return text.substring(0, 45);
    }

    private FileJPA getFile(BasicFile basicFile) {
        return FileJPA.builder()
                .fileId(basicFile.getFileId())
                .fileType(FileType.valueOf(basicFile.getFileType().name()))
                .fileUniqueId(basicFile.getFileUniqueId())
                .fileSize(basicFile.getFileSize())
                .fileName(basicFile.getFileName())
                .duration(basicFile.getDuration())
                .height(basicFile.getHeight())
                .emoji(basicFile.getEmoji())
                .width(basicFile.getWidth())
                .isAnimated(basicFile.getIsAnimated())
                .isVideo(basicFile.getIsVideo())
                .mimeType(basicFile.getMimeType())
                .build();
    }

    private boolean isNodeMainFieldsNotEmpty(QuestNodeJPA node) {
        var nodeType = node.getNodeType();
        return switch (nodeType) {
            case QuestNodeType.MESSAGE ->
                    (node.getMainText() != null && !node.getMainText().isBlank()) || !node.getMediaList().isEmpty();
            case QuestNodeType.POLL -> node.getMainText() != null && !node.getMainText().isBlank();
            case QuestNodeType.GEO -> node.getGeoPoint().getLatitude() != null;
            case QuestNodeType.MEDIA -> !node.getMediaList().isEmpty();
        };
    }

    private QuestNodeJPA getNode(Long nodeId) {
        var nodeOptional = nodeRepository.findById(nodeId);
        if (nodeOptional.isEmpty()) {
            throw new RuntimeException("А ноды то почему-то нету, но это странно");
        }
        return nodeOptional.get();
    }

}
