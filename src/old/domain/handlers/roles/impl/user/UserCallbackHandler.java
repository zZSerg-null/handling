package ru.old.domain.handlers.roles.impl.user;

import lombok.extern.slf4j.Slf4j;
import ru.old.domain.handlers.CallbackHandler;
import old.domain.handlers.h1.BasicMessageBuilder;
import ru.old.domain.handlers.roles.constants.CallbackConstants;
import ru.old.domain.handlers.roles.constants.MessageConstants;
import ru.old.domain.handlers.roles.impl.creator.utils.CreatorMessageTextSelector;
import org.springframework.stereotype.Service;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.RespMessageType;
import old.domain.enums.RetryType;
import ru.old.domain.handlers.roles.impl.BasicHandler;
import old.domain.handlers.h1.keyboard.UserKeyboardsKeeper;
import old.domain.jpa.service.UserRepositoryService;

import java.util.List;

@Slf4j
@Service("userCallbackHandler")
public class UserCallbackHandler extends BasicHandler implements CallbackHandler {

    private final UserRepositoryService userRepositoryService;
    private final UserKeyboardsKeeper keyboardsKeeper;


    public UserCallbackHandler(BasicMessageBuilder messageBuilder, CreatorMessageTextSelector textSelector, UserRepositoryService userRepositoryService, UserKeyboardsKeeper keyboardsKeeper) {
        super(messageBuilder, textSelector);
        this.userRepositoryService = userRepositoryService;
        this.keyboardsKeeper = keyboardsKeeper;
    }

    @Override
    public QuestResponseData processCallback(RequestData request, UserInfo userInfo) {
        var callback = request.getCallbackData().getData().split(":")[0];
        var builder = QuestResponseData.builder();

        List<RespMessageData> respMessageDataList =
                switch (callback) {
                    case CallbackConstants.USER_QUEST_MENU_CREATE ->
                            createQuest(request, userInfo); // Реакция на нажатие кнопки "Создать квест"
                    case CallbackConstants.USER_QUEST_MENU_SHOW_ALL ->
                            showAllQuests(request, userInfo); // Реакция на нажатие кнопки "Мои квесты"
                    case CallbackConstants.USER_QUEST_MENU_RUNNING ->
                            showRunningQuests(request, userInfo); // Реакция на нажатие кнопки "Запущенные квесты"

                    // Реакция на нажатие кнопки "Назад", провалившись в любое меню
                    case CallbackConstants.USER_MAIN_MENU -> getUserMainMenu(request, userInfo);

                    // Реакция на кнопки аккаунта. Моки
                    case CallbackConstants.USER_ACCOUNT_MENU_STATISTIC -> statistics(request, userInfo);
                    case CallbackConstants.USER_ACCOUNT_MENU_CHANGE_NAME -> changeName(request, userInfo);


                  //  case CallbackConstants.USER_CHANGE_NAME_CONFIRM ->();

                    default -> unknownCallbackAction(request, userInfo, callback);
                };

        return builder.messageDataList(respMessageDataList)
                .build();
    }

    private List<RespMessageData> getUserMainMenu(RequestData request, UserInfo userInfo) {
        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setReplyMarkup(keyboardsKeeper.getUserQuestMenuKeyboard(userInfo));
        message.setText(MessageConstants.USER_MAIN_MENU);
        return List.of(removeMessage, message);
    }

    /**
     * Устанавливает статус в UserRole.CREATOR что переключает обработку на Creator handlers
     * создается сам квест и QuestCreationInfo
     **/
    private List<RespMessageData> createQuest(RequestData request, UserInfo userInfo) {
        userRepositoryService.startQuestCreation(userInfo);

        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setText(MessageConstants.NEW_QUEST_WAS_CREATED);
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> showAllQuests(RequestData request, UserInfo userInfo) {
        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setText(MessageConstants.QUEST_MENU_SHOW_ALL);
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> showRunningQuests(RequestData request, UserInfo userInfo) {
        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setText(MessageConstants.QUEST_MENU_RUNNING_QUESTS);
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> statistics(RequestData request, UserInfo userInfo) {
        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setText(MessageConstants.ACCOUNT_MENU_STATISTIC);
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> changeName(RequestData request, UserInfo userInfo) {
        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setText(MessageConstants.ACCOUNT_MENU_CHANGE_NAME);
        message.setReplyMarkup(keyboardsKeeper.getUserChangingNameKeyboard());
        return List.of(removeMessage, message);
    }

    private List<RespMessageData> unknownCallbackAction(RequestData request, UserInfo userInfo, String callback) {
        log.warn("CreatorCallbackHandler: неожиданный колбэк: [{}] от юзера: {}", callback, userInfo);

        var removeMessage = messageBuilder.buildMessage(request, RespMessageType.DELETE_MESSAGE, RetryType.IGNORE);
        var message = messageBuilder.buildUnexpectedMessage(request);
        return List.of(removeMessage, message);
    }
}
