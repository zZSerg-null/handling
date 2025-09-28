package ru.old.domain.handlers.roles.impl.creator;

import lombok.extern.slf4j.Slf4j;
import ru.old.domain.handlers.MessageHandler;
import old.domain.handlers.h1.BasicMessageBuilder;
import ru.old.domain.handlers.roles.constants.MessageConstants;
import old.domain.handlers.roles.impl.creator.utils.*;
import org.springframework.stereotype.Service;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.CreationStage;
import old.domain.enums.RespMessageType;
import old.domain.enums.RetryType;
import ru.old.domain.handlers.roles.impl.CreatorHandler;
import ru.quest_bot.data_handler_service.domain.handlers.roles.impl.creator.utils.*;
import ru.quest_bot.data_handler_service.old.domain.handlers.roles.impl.creator.utils.*;
import old.domain.jpa.service.CreationInfoService;
import old.domain.jpa.service.LastResponseJPARepositoryService;
import old.domain.jpa.service.QuestNodeRepositoryService;
import old.domain.jpa.service.UserRepositoryService;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service("creatorMessageHandler")
public class CreatorMessageHandler extends CreatorHandler implements MessageHandler {

    private static final int ADDING_STAGE_MIN_LEVEL = 4;
    private static final String ADDING_STAGE_PREFIX = "ADDING";

    private final NodeEditingService nodeEditingService;
    private final QuestMapControlsService questMapControlsService;


    public CreatorMessageHandler(BasicMessageBuilder messageBuilder, CreatorMessageTextSelector textSelector, UserRepositoryService userService, CreationInfoService infoService, QuestNodeRepositoryService nodeService, CreatorMenuSelector menuSelector, QuestMapService questMapService, LastResponseJPARepositoryService lastResponseService, NodeEditingService nodeEditingService, QuestMapControlsService questMapControlsService) {
        super(messageBuilder, textSelector, userService, infoService, nodeService, menuSelector, questMapService, lastResponseService);
        this.nodeEditingService = nodeEditingService;
        this.questMapControlsService = questMapControlsService;
    }

    @Override
    public QuestResponseData processMessage(RequestData request, UserInfo userInfo) {
        System.err.println("CreatorMessageHandler");

        if (request.getMessage().getText() != null) {
            return textCommand(request, userInfo);
        }

        return nodeEditingService.setData(request, userInfo);
    }

    private QuestResponseData textCommand(RequestData request, UserInfo userInfo) {
        String[] commands = request.getMessage().getText().split(" ");
        String command = commands[0];
        System.out.println(command);
        System.out.println(Arrays.toString(commands));


        List<RespMessageData> responseList =
                switch (command) {
                    case MessageConstants.UPDATE_COMMAND -> updateMenu(request, userInfo);

                    case MessageConstants.QUEST_COMMAND, MessageConstants.ACCOUNT_COMMAND ->
                            questCreationStopQuestion(request, userInfo);

                    // reply keyboard
                    case MessageConstants.APPLY_COMMAND ->
                            applyAndReturn(request, userInfo); //Пользователь сохранил то, что он добавил
                    case MessageConstants.SET_NULL_COMMAND ->
                            setDefaultData(request, userInfo); //пользователь обнулил поле

                    // обработка команд управления картой квеста (изменить ноду, поднять, опустить, удалить)
                    case MessageConstants.EDIT_COMMAND -> editAction(request, userInfo, commands[1]);

                    default -> unknownTextAction(request, userInfo, command);
                };

        return QuestResponseData.builder()
                .messageDataList(responseList)
                .build();
    }

    private List<RespMessageData> editAction(RequestData request, UserInfo userInfo, String command) {
        if (!command.startsWith("editCommand")) {
            return unknownTextAction(request, userInfo, "ХЗ что это: 1");
        }

        String[] editActionCommand = command.split("_");

        if (editActionCommand.length < 2) {
            return unknownTextAction(request, userInfo, "ХЗ что это: 2");
        }
        long nodeId = Long.parseLong(editActionCommand[2]);
        System.out.println("comand: "+editActionCommand[1]);
        return switch (editActionCommand[1]) {
            case "moveUp" -> questMapControlsService.moveSelectedNodeUp(request, userInfo, nodeId);
            case "moveDown" -> questMapControlsService.moveSelectedNodeDown(request, userInfo, nodeId);
            case "delete" -> questMapControlsService.removeSelectedNode(request, userInfo, nodeId);
            case "editNode" -> questMapControlsService.editSelectedNode(request, userInfo, nodeId);
            default -> unknownTextAction(request, userInfo, "Пока делаю");
        };
    }

    private List<RespMessageData> applyAndReturn(RequestData request, UserInfo userInfo) {
        var stageList = userInfo.getCreationInfo().getCreationStageList();

        // проверям что текущий этап создания - это добавление. Что бы не реагировать на сообщения набранные руками
        // или отправленные случайно (как-нибудь)
        if (stageList.size() < ADDING_STAGE_MIN_LEVEL || !stageList.peekLast().name().contains(ADDING_STAGE_PREFIX)) {
            return List.of(messageBuilder.buildUnexpectedMessage(request));
        }

        stageList.pollLast(); // Удаляем последний элемент, описывающий что именно мы добавляли.
        var currentStage = stageList.peekLast(); // Получаем элемент, говорящий о том, какое меню предшествовало добавлению
        infoService.updateStageList(userInfo.getCreationInfo()); //обновляем это в базе

        //Удаляем клавиатурку
        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.IGNORE);
        removeMessage.setReplyMarkup(menuSelector.getReplyRemoveKeyboard());
        removeMessage.setText("Данные успешно сохранены");

        //показываем предыдущее меню
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setReplyMarkup(menuSelector.getMenuByStage(currentStage, userInfo.getCreationInfo().getMenuNodeInfo()));
        message.setText(textSelector.selectTextByStage(currentStage));
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> setDefaultData(RequestData request, UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();
        if (list == null || list.isEmpty() || !list.getLast().name().startsWith("ADDING")) {
            return unknownTextAction(request, userInfo, null);
        }
        return nodeEditingService.setDefault(request, userInfo);
    }

    private List<RespMessageData> questCreationStopQuestion(RequestData request, UserInfo userInfo) {
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.IGNORE);
        message.setReplyMarkup(menuSelector.getMenuByStage(CreationStage.STOP_CREATING, userInfo.getCreationInfo().getMenuNodeInfo()));
        message.setText("Хотите завершить создание квеста'" + userInfo.getCreationInfo().getQuestName() + "'?");
        return List.of(message);
    }

    private List<RespMessageData> updateMenu(RequestData request, UserInfo userInfo) {
        return lastResponseService.getLastServiceResponseToUser(request, userInfo.getStatus());
    }

    private List<RespMessageData> unknownTextAction(RequestData request, UserInfo userInfo, String messageText) {
        var list = userInfo.getCreationInfo().getCreationStageList();

        if (!list.isEmpty()) {
            var stage = list.peekLast();

            return switch (stage) {
                case NEW_QUEST_AWATING_NAME_CONFIRMATION -> nodeEditingService.confirmQuestName(request, userInfo);

                case ADDING_TEXT -> nodeEditingService.addingText(request, userInfo);

                case ADDING_EXPECTED_ANSWERS -> nodeEditingService.addingExpectedAnswers(request, userInfo);
                case ADDING_EXPECTED_ANSWERS_COUNT -> nodeEditingService.addingExpectedAnswersCount(request, userInfo);
                case ADDING_CORRECT_MESSAGE_REACT -> nodeEditingService.addingCorrectAnswersReact(request, userInfo);
                case ADDING_WRONG_MESSAGE_REACT -> nodeEditingService.addingWrongMessageReact(request, userInfo);

                case ADDING_GEOPOINT_RADIUS_VALUE -> nodeEditingService.addingGeopointRadiusValue(request, userInfo);
                case ADDING_GEOPOINT_RADIUS_MESSAGES ->
                        nodeEditingService.addingGeopointRadiusMessage(request, userInfo);

                case ADDING_POLL_NODE_ANSWERS -> nodeEditingService.addingPollAnswers(request, userInfo);
                case ADDING_POLL_NODE_QUESTION -> nodeEditingService.addingPollQuestion(request, userInfo);
                case ADDING_POLL_NODE_EXPLANATION -> nodeEditingService.addingPollExplanation(request, userInfo);

                default -> List.of(messageBuilder.buildUnexpectedMessage(request));
            };
        }

        log.warn("CreatorMessageHandler: неожиданная команда: [{}] от юзера: {}", messageText, userInfo);
        return List.of(messageBuilder.buildUnexpectedMessage(request));
    }

}
