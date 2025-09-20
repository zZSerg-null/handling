package ru.old.domain.handlers.roles.impl.creator;

import lombok.extern.slf4j.Slf4j;
import ru.old.domain.handlers.CallbackHandler;
import old.domain.handlers.h1.BasicMessageBuilder;
import ru.old.domain.handlers.roles.constants.CallbackConstants;
import org.springframework.stereotype.Service;
import old.domain.dto.CreationInfo;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.CreationStage;
import old.domain.enums.RespMessageType;
import old.domain.enums.RetryType;
import ru.old.domain.handlers.roles.impl.CreatorHandler;
import old.domain.handlers.roles.impl.creator.utils.CreatorMenuSelector;
import old.domain.handlers.roles.impl.creator.utils.CreatorMessageTextSelector;
import old.domain.handlers.roles.impl.creator.utils.QuestMapService;
import old.domain.jpa.service.CreationInfoService;
import old.domain.jpa.service.LastResponseJPARepositoryService;
import old.domain.jpa.service.QuestNodeRepositoryService;
import old.domain.jpa.service.UserRepositoryService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
@Service("creatorCallbackHandler")
public class CreatorCallbackHandler extends CreatorHandler implements CallbackHandler {

    public CreatorCallbackHandler(BasicMessageBuilder messageBuilder, CreatorMessageTextSelector textSelector,
                                  UserRepositoryService userService, CreationInfoService infoService,
                                  QuestNodeRepositoryService nodeService, CreatorMenuSelector menuSelector,
                                  QuestMapService questMapService, LastResponseJPARepositoryService lastResponseService) {
        super(messageBuilder, textSelector, userService, infoService, nodeService, menuSelector, questMapService, lastResponseService);
    }

    @Override
    public QuestResponseData processCallback(RequestData request, UserInfo userInfo) {
        System.err.println("CreatorCallbackHandler");
        var callback = request.getCallbackData().getData().split(":")[0];
        var builder = QuestResponseData.builder();

        var respMessageDataList =
                switch (callback) {
                    case CallbackConstants.EDIT_NODE_SELECT_NODE -> selectNodeMenu(request, userInfo);

                    /**
                     * QUEST_CREATION_STOP: нажатие на кнопку "Завершить создание", а так же "Да, хочу"
                     * при нажатии кнопок в меню создания нод и диалоге прекращения создания квеста
                     * QUEST_CREATION_CONTINUE: "Нет, продолжу" в диалоге прекращения создания квеста и "Подумаю еще"
                     * в диалоге подтверждения имени квеста
                     */
                    case CallbackConstants.QUEST_CREATION_STOP -> stopQuestCreation(request, userInfo);
                    case CallbackConstants.QUEST_CREATION_CONTINUE -> {
                        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
                        yield List.of(removeMessage);
                    }


                    /**
                     * нажатие кнопки "Оставить это название" в диалоге подтверждения имени квеста
                     */
                    case CallbackConstants.QUEST_CREATION_ACCEPT_NAME -> questNameAccepted(request, userInfo);


                    /**
                     * нажатие кнопок "Да, хочу" и "Нет конечно!" в диалоге подтверждения сброса данных ноды
                     */
                    case CallbackConstants.DELETE_NODE -> deleteNode(request, userInfo);
                    case CallbackConstants.CONTINUE_NODE_CREATION -> continueCreating(request, userInfo);


                    /**
                     * добавление текста ко всем типам нод кроме Медаигруппы
                     * добавление файлов к обычному сообщению и медиагруппе
                     */
                    case CallbackConstants.NODE_MENU_HELP ->
                            nodeMenuAction(request, userInfo, CreationStage.NODE_MENU_HELP);
                    case CallbackConstants.NODE_MENU_MESSAGE ->
                            nodeMenuAction(request, userInfo, CreationStage.NODE_MENU_MESSAGE);
                    case CallbackConstants.NODE_MENU_POLL ->
                            nodeMenuAction(request, userInfo, CreationStage.NODE_MENU_POLL);
                    case CallbackConstants.NODE_MENU_GEO ->
                            nodeMenuAction(request, userInfo, CreationStage.NODE_MENU_GEO);
                    case CallbackConstants.NODE_MENU_MEDIAGROUP ->
                            nodeMenuAction(request, userInfo, CreationStage.NODE_MENU_MEDIAGROUP);

                    /**
                     * подменю геоноды - радиус, в котором можно установить числовую величину и сообщение
                     */
                    case CallbackConstants.SUBMENU_GEO_RADIUS ->
                            subMenuAction(request, userInfo, CreationStage.SUBMENU_GEOPOINT_RADIUS_MENU);
                    /**
                     * подменю всех типов нод для установки ожидаемых ответов и реакций на ответы
                     */
                    case CallbackConstants.SUBMENU_EXPECTED_ANSWERS ->
                            subMenuAction(request, userInfo, CreationStage.SUBMENU_EXPECTED_ANSWERS_MENU);


                    /**
                     * добавление текста ко всем типам нод кроме Медаигруппы
                     * добавление файлов к обычному сообщению и медиагруппе
                     */
                    case CallbackConstants.ADDING_TEXT -> addMenuAction(request, userInfo, CreationStage.ADDING_TEXT);
                    case CallbackConstants.ADDING_MESSAGE_NODE_FILE ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_FILE);


                    /**
                     * заполнение полей у POLL ноды
                     */
                    case CallbackConstants.ADDING_POLL_NODE_QUESTION ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_POLL_NODE_QUESTION);
                    case CallbackConstants.ADDING_POLL_NODE_ANSWERS ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_POLL_NODE_ANSWERS);
                    case CallbackConstants.ADDING_POLL_NODE_EXPLANATION ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_POLL_NODE_EXPLANATION);


                    /**
                     * заполнение полей у гео ноды
                     */
                    case CallbackConstants.ADDING_GEOPOINT ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_GEOPOINT);
                    case CallbackConstants.ADDING_GEOPOINT_RADIUS_VALUE ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_GEOPOINT_RADIUS_VALUE);
                    case CallbackConstants.ADDING_GEOPOINT_RADIUS_MESSAGES ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_GEOPOINT_RADIUS_MESSAGES);


                    /**
                     * добавление ожидаемых ответов и реакций ко всем типам нод
                     */
                    case CallbackConstants.ADDING_EXPECTED_ANSWERS ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_EXPECTED_ANSWERS);
                    case CallbackConstants.ADDING_EXPECTED_ANSWERS_COUNT ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_EXPECTED_ANSWERS_COUNT);
                    case CallbackConstants.ADDING_CORRECT_ANSWERS_REACTIONS ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_CORRECT_MESSAGE_REACT);
                    case CallbackConstants.ADDING_WRONG_ANSWERS_REACTIONS ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_WRONG_MESSAGE_REACT);
                    case CallbackConstants.ADDING_MEDIA_GROUP ->
                            addMenuAction(request, userInfo, CreationStage.ADDING_MEDIA_GROUP);

                    /**
                     * нажатие кнопки "Назад" в меню создания
                     */
                    case CallbackConstants.CREATION_MENU_BACK_BUTTON -> backButtonAction(request, userInfo);


                    case CallbackConstants.EDIT_NODE_VIEW_LIST -> editModeOn(request, userInfo);
                    case CallbackConstants.EDIT_NODE_BACK_BUTTON -> editModeOff(request, userInfo);

                    /**
                     * нажатие кнопки "Сохранить и вернуться" в корневом меню создания любой из нод для перехода
                     * к выбору списка типов нод
                     */
                    case CallbackConstants.SAVE_NODE_DATA -> saveNodeAndGetMainCreationMenu(request, userInfo); //todo


                    /**
                     * колбэк, которого тут не должно быть: колбыки прочих ролей
                     */
                    default -> unknownCallbackAction(request, userInfo);
                };


        return builder.messageDataList(respMessageDataList)
                .build();
    }

    private List<RespMessageData> selectNodeMenu(RequestData request, UserInfo userInfo) {
        var dataArr = request.getCallbackData().getData().split(":");
        if (dataArr.length < 2) {
            throw new RuntimeException("Нет данных о ноде");
        }
        long nodeId = Long.parseLong(dataArr[1]);

        infoService.setNode(userInfo.getCreationInfo(), nodeId);
        return null;
    }

    private List<RespMessageData> editModeOff(RequestData request, UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();

        Iterator<CreationStage> iterator = list.descendingIterator();
        while (iterator.hasNext()) {
            CreationStage stage = iterator.next();
            if (stage.equals(CreationStage.MAIN_MENU_NODE_TYPE_SELECTION)) {
                break;
            }
            iterator.remove();
        }

        infoService.updateStageList(userInfo.getCreationInfo());
        return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
    }


    /**
     * проверка возможности срабатывани кнопки выбора типа ноды
     */
    private List<RespMessageData> nodeMenuAction(RequestData request, UserInfo userInfo, CreationStage stage) {
        if (!validateNodeMenu(userInfo)) {
            return unknownCallbackAction(request, userInfo);
        }

        userInfo.getCreationInfo().getCreationStageList().add(stage);
        return doAction(request, userInfo);
    }

    /**
     * проверка возможности срабатывани кнопки выбора подменю
     */
    private List<RespMessageData> subMenuAction(RequestData request, UserInfo userInfo, CreationStage stage) {
        if (!validateSubMenu(userInfo)) {
            return unknownCallbackAction(request, userInfo);
        }

        userInfo.getCreationInfo().getCreationStageList().add(stage);
        return doAction(request, userInfo);
    }

    /**
     * проверка возможности срабатывани кнопки добавления данных к ноде
     */
    private List<RespMessageData> addMenuAction(RequestData request, UserInfo userInfo, CreationStage stage) {
        if (!validateAddMenu(userInfo)) {
            return unknownCallbackAction(request, userInfo);
        }

        userInfo.getCreationInfo().getCreationStageList().add(stage);
        return doAction(request, userInfo);
    }

    /**
     * обработка нажатия кнопки
     */
    private List<RespMessageData> doAction(RequestData request, UserInfo userInfo) {
        var stageList = userInfo.getCreationInfo().getCreationStageList();
        if (stageList == null || stageList.isEmpty()) {
            return unknownCallbackAction(request, userInfo);
        }

        infoService.updateStageList(userInfo.getCreationInfo());
        CreationStage stage = stageList.peekLast();

        //если нужно, создаем ноду,
        if (stage.equals(CreationStage.NODE_MENU_MESSAGE) ||
                stage.equals(CreationStage.NODE_MENU_POLL) ||
                stage.equals(CreationStage.NODE_MENU_GEO) ||
                stage.equals(CreationStage.NODE_MENU_MEDIAGROUP)) {
            nodeService.createNode(stage, userInfo.getCreationInfo());
        }
        return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
    }

    private List<RespMessageData> continueCreating(RequestData request, UserInfo userInfo) {
        if (!validateSubMenu(userInfo)) {
            return unknownCallbackAction(request, userInfo);
        }
        return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
    }

    private List<RespMessageData> saveNodeAndGetMainCreationMenu(RequestData request, UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();
        if (list != null && !list.isEmpty() && list.size() > 2 && list.get(list.size() - 2).equals(CreationStage.MAIN_MENU_NODE_TYPE_SELECTION)) {
            userInfo.getCreationInfo().getCreationStageList().pollLast();
            infoService.updateStageList(userInfo.getCreationInfo());
            nodeService.isNodeEmptyMainFields(userInfo.getCreationInfo());
            return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
        }

        return unknownCallbackAction(request, userInfo);
    }

    private List<RespMessageData> deleteNode(RequestData request, UserInfo userInfo) {
        if (!validateSubMenu(userInfo)) {
            return unknownCallbackAction(request, userInfo);
        }
        userInfo.getCreationInfo().getCreationStageList().pollLast();
        nodeService.deleteNode(userInfo.getCreationInfo());
        return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
    }

    private List<RespMessageData> questNameAccepted(RequestData request, UserInfo userInfo) {
        if (!validateMainMenu(userInfo)) {
            return unknownCallbackAction(request, userInfo);
        }

        userInfo.getCreationInfo().getCreationStageList().add(CreationStage.MAIN_MENU_NODE_TYPE_SELECTION);
        infoService.updateStageList(userInfo.getCreationInfo());
        return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
    }


    /**
     * ОБРАБОТКА НАЖАТИЯ КНОПКИ "Назад" В ПОДМЕНЮШКАХ СОЗДАНИЯ
     */
    private List<RespMessageData> backButtonAction(RequestData request, UserInfo userInfo) {
        var stageList = userInfo.getCreationInfo().getCreationStageList();

        if (stageList.size() <= 2) {
            return unknownCallbackAction(request, userInfo);
        }

        int lastElement = stageList.size() - 1;
        if (!stageList.get(lastElement).name().contains("MENU")) {
            log.warn("Нажатие на kнопку 'НАЗАД' там, где ее не должно быть");
            return unknownCallbackAction(request, userInfo);
        }

        //Удаляем последний элемент и сохраняем
        stageList.pollLast();
        infoService.updateStageList(userInfo.getCreationInfo());
        return getMessageListByCreationInfo(request, userInfo.getCreationInfo(), null);
    }

    private List<RespMessageData> editModeOn(RequestData request, UserInfo userInfo) {
        var nodeInfoList = nodeService.getNodeListByQuestId(userInfo.getCreationInfo());

        List<RespMessageData> respMessageDataList = new ArrayList<>();

        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        if (nodeInfoList.isEmpty()) {
            message.setText("Вы пока не создали ни одного этапа");
        } else {
            var deleteMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
            respMessageDataList.add(deleteMessage);

            userInfo.getCreationInfo().getCreationStageList().add(CreationStage.EDIT_NODE_VIEW_LIST);
            infoService.updateStageList(userInfo.getCreationInfo());
            message.setText(questMapService.getQuestMapMenu(nodeInfoList));
            message.setParseMode("HTML");
        }
        respMessageDataList.add(message);

        return respMessageDataList;
    }

    private List<RespMessageData> stopQuestCreation(RequestData request, UserInfo userInfo) {
        userService.stopQuestCreation(userInfo);

        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setText("Вы завершили создание квеста");
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> unknownCallbackAction(RequestData request, UserInfo userInfo) {
        String callback = request.getCallbackData().getData();
        log.warn("CreatorCallbackHandler: неожиданный колбэк: [{}] от юзера: {}", callback, userInfo);

        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildUnexpectedMessage(request);
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> getMessageListByCreationInfo(RequestData request, CreationInfo info, String text) {
        var stage = info.getCreationStageList().peekLast();

        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setReplyMarkup(menuSelector.getMenuByStage(stage, info.getMenuNodeInfo()));
        if (text == null) {
            message.setText(textSelector.selectTextByStage(stage));
        } else {
            message.setText(text);
        }
        return List.of(removeMessage, message);
    }


    private boolean validateMainMenu(UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();
        return list != null && !list.isEmpty() && list.peekLast().equals(CreationStage.NEW_QUEST_AWATING_NAME_CONFIRMATION);
    }

    private boolean validateNodeMenu(UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();
        return list != null && !list.isEmpty() && list.peekLast().equals(CreationStage.MAIN_MENU_NODE_TYPE_SELECTION);
    }

    private boolean validateSubMenu(UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();
        return list != null && !list.isEmpty() && list.peekLast().name().startsWith("NODE_MENU");
    }

    private boolean validateAddMenu(UserInfo userInfo) {
        var list = userInfo.getCreationInfo().getCreationStageList();
        return list != null && !list.isEmpty() &&
                (list.peekLast().name().startsWith("NODE_MENU") | list.peekLast().name().startsWith("SUBMENU"));
    }

}
