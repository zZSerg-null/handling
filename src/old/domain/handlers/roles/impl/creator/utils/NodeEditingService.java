package ru.old.domain.handlers.roles.impl.creator.utils;

import lombok.RequiredArgsConstructor;
import old.domain.handlers.h1.BasicMessageBuilder;
import org.springframework.stereotype.Component;
import old.domain.dto.CreationInfo;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.CreationStage;
import old.domain.enums.RespMessageType;
import old.domain.enums.RetryType;
import old.domain.jpa.service.QuestNodeRepositoryService;
import old.domain.jpa.service.QuestRepositoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class NodeEditingService {

    private final BasicMessageBuilder messageBuilder;
    private final QuestRepositoryService questService;
    private final QuestNodeRepositoryService nodeService;
    private final CreatorMenuSelector menuSelector;


    public List<RespMessageData> confirmQuestName(RequestData request, UserInfo userInfo) {
        var messageText = request.getMessage().getText();

        //Записываем имя в квест
        questService.addQuestName(userInfo.getCreationInfo().getQuestId(), messageText);

        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);

        //если такое имя уже существует, спрашиваем хочет ли пользователь несколько квестов с одним именем
        if (userInfo.getUserQuestsInfo().getQuestNames().contains(messageText.toLowerCase())) {
            message.setText("В ваших квестах уже есть квест с названием '" + messageText + "'. Хотите ли вы добавить еще один с таким же именем?");
        } else
            message.setText("Оставить название '" + messageText + "'?");

        message.setReplyMarkup(menuSelector.getMenuByStage(CreationStage.NEW_QUEST_AWATING_NAME_CONFIRMATION, userInfo.getCreationInfo().getMenuNodeInfo()));
        return List.of(message);
    }

    public QuestResponseData setData(RequestData request, UserInfo userInfo) {
        List<RespMessageData> list = new ArrayList<>();

        if (request.getMessage().getFile() != null) {
            list = addingFile(request, userInfo);
        } else if (request.getMessage().getLocation() != null) {
            list = addingGeopoint(request, userInfo);
        }

        return QuestResponseData.builder()
                .messageDataList(list)
                .build();
    }

    public List<RespMessageData> addingText(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_TEXT)) {
            return defaultMessage(request, userInfo, "Что-то пошло не так");
        }
        nodeService.addMainText(userInfo, request.getMessage().getText());
        return defaultMessage(request, userInfo, "Текст успешно добавлен");
    }

    public List<RespMessageData> addingFile(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_FILE) && !validate(userInfo.getCreationInfo(), CreationStage.ADDING_MEDIA_GROUP)) {
            return defaultMessage(request, userInfo, "Что-то пошло не так");
        }

        nodeService.addFiles(userInfo.getCreationInfo(), request.getMessage().getFile());
        return defaultMessage(request, userInfo, "Файл успешно добавлен");
    }

    public List<RespMessageData> addingExpectedAnswers(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_EXPECTED_ANSWERS)) {
            return defaultMessage(request, userInfo, "Что-то пошло не так");
        }

        var answers = nodeService.addExpectedAnswers(userInfo.getCreationInfo(), request.getMessage().getText());
        return defaultMessage(request, userInfo, "Ожидаемые ответы игрока: " + answers.toString());
    }

    public List<RespMessageData> addingExpectedAnswersCount(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_EXPECTED_ANSWERS_COUNT)) {
            return defaultMessage(request, userInfo, "Что-то пошло не так");
        }
        int radius = parseInteger(request.getMessage().getText());
        if (radius == Integer.MIN_VALUE) {
            return defaultMessage(request, userInfo, "Введенное значение не является числом");
        }
        if (radius <= 1) {
            return defaultMessage(request, userInfo, "Количество ожидаемых ответов не может быть меньше одного");
        }

        String responseText = nodeService.addExpectedAnswersCount(userInfo.getCreationInfo(), radius);
        return defaultMessage(request, userInfo, responseText);
    }

    public List<RespMessageData> addingCorrectAnswersReact(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_EXPECTED_ANSWERS_COUNT)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        var answersReact = nodeService.addCorrectAnswersReact(userInfo.getCreationInfo(), request.getMessage().getText());
        return defaultMessage(request, userInfo, "Реакции игрока: " + answersReact.toString());
    }

    public List<RespMessageData> addingWrongMessageReact(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_WRONG_MESSAGE_REACT)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        var answersReact = nodeService.addWrongMessageReact(userInfo.getCreationInfo(), request.getMessage().getText());
        return defaultMessage(request, userInfo, "Реакции игрока: " + answersReact.toString());
    }

    public List<RespMessageData> addingGeopoint(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_GEOPOINT)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        nodeService.addGeopoint(userInfo.getCreationInfo(), request.getMessage().getLocation());
        return defaultMessage(request, userInfo, "Указанная точка успешно добавлена");
    }

    public List<RespMessageData> addingGeopointRadiusValue(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_GEOPOINT_RADIUS_VALUE)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        int radius = parseInteger(request.getMessage().getText());
        if (radius == Integer.MIN_VALUE) {
            return defaultMessage(request, userInfo, "Введенное значение не является числом");
        }
        if (radius <= 15) {
            return defaultMessage(request, userInfo, "Радиус активности не может быть меньше 15 метров");
        }

        nodeService.addGeopointRadiusValue(userInfo.getCreationInfo(), radius);
        return defaultMessage(request, userInfo, "Радиус установлен в " + radius + " метров");
    }

    public List<RespMessageData> addingGeopointRadiusMessage(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_GEOPOINT_RADIUS_MESSAGES)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        nodeService.addGeopointRadiusMessage(userInfo.getCreationInfo(), request.getMessage().getText());
        return defaultMessage(request, userInfo, "Сообщение записано, оно будет показано пользователю при входе в радиус");
    }

    public List<RespMessageData> addingPollQuestion(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_POLL_NODE_QUESTION)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        nodeService.addPollQuestion(userInfo.getCreationInfo(), request.getMessage().getText());
        return defaultMessage(request, userInfo, null);
    }

    public List<RespMessageData> addingPollAnswers(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_POLL_NODE_ANSWERS)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        List<String> answers = Arrays.stream(request.getMessage().getText().split(";")).toList();
        if (answers.size() < 2){
            return defaultMessage(request, userInfo, "Вариантов ответа должно быть как минимум 2. Попробуйте еще раз");
        }

        nodeService.addPollAnswers(userInfo.getCreationInfo(), answers);
        return defaultMessage(request, userInfo, null);
    }

    public List<RespMessageData> addingPollExplanation(RequestData request, UserInfo userInfo) {
        if (!validate(userInfo.getCreationInfo(), CreationStage.ADDING_POLL_NODE_EXPLANATION)){
            return defaultMessage(request, userInfo,"Что-то пошло не так");
        }
        nodeService.addPollExplanation(userInfo.getCreationInfo(), request.getMessage().getText());
        return defaultMessage(request, userInfo, null);
    }

    public List<RespMessageData> setDefault(RequestData request, UserInfo userInfo) {
        nodeService.setDefaultFieldData(userInfo.getCreationInfo());
        return defaultMessage(request, userInfo, "Значение сброшено");
    }

    private List<RespMessageData> defaultMessage(RequestData request, UserInfo userInfo, String messageText) {
        var stage = userInfo.getCreationInfo().getCreationStageList().pollLast();

        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.IGNORE);
        if (!stage.name().startsWith("ADDING")){
            message.setText("Я не понимаю, что происходит, похоже что то пошло не так ");
            message.setReplyMarkup(menuSelector.getReplyAddKeyboard());
            return List.of(message);
        }


        message.setText(messageText);
        message.setReplyMarkup(menuSelector.getReplyAddKeyboard());
        return List.of(message);

//        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.IGNORE);
//        removeMessage.setReplyMarkup(menuSelector.getReplyAddKeyboard());
//        removeMessage.setText(messageText);
//
//        String responseText = textSelector.selectTextByStage(stage);
//        RespMessageData message;
//        if (responseText.equals(MessageConstants.UNKNOWN_DATA)) {
//            message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.IGNORE);
//        } else {
//            message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
//        }
//
//        message.setText(responseText);
//        message.setReplyMarkup(menuSelector.getMenuByStage(stage));
//        return List.of(removeMessage, message);
    }

    private boolean validate(CreationInfo creationInfo, CreationStage expectedStage) {
        return creationInfo != null &&
                creationInfo.getCreationStageList() != null &&
                !creationInfo.getCreationStageList().isEmpty() &&
                creationInfo.getCreationStageList().peekLast().equals(expectedStage);
    }

    private int parseInteger(String text) {
        int radius;
        try {
            radius = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            radius = Integer.MIN_VALUE;
        }
        return radius;
    }
}
