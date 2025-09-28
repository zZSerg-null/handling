package ru.old.domain.handlers.roles.impl.user;

import lombok.extern.slf4j.Slf4j;
import ru.old.domain.handlers.MessageHandler;
import old.domain.handlers.h1.BasicMessageBuilder;
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
import old.domain.jpa.service.LastResponseJPARepositoryService;

import java.util.List;

@Slf4j
@Service("userMessageHandler")
public class UserMessageHandler extends BasicHandler implements MessageHandler {

    private final LastResponseJPARepositoryService lastUserResponseService;

    protected final UserKeyboardsKeeper keyboardsKeeper;

    public UserMessageHandler(BasicMessageBuilder messageBuilder, CreatorMessageTextSelector textSelector, LastResponseJPARepositoryService lastUserResponseService, UserKeyboardsKeeper keyboardsKeeper) {
        super(messageBuilder, textSelector);
        this.lastUserResponseService = lastUserResponseService;
        this.keyboardsKeeper = keyboardsKeeper;
    }

    @Override
    public QuestResponseData processMessage(RequestData request, UserInfo userInfo) {
        var responseData = QuestResponseData.builder();

        if (request.getMessage().getFile() != null) {
            responseData.messageDataList(unknownTextAction(request,userInfo, "касса не обслуживает"));

            //responseData.messageDataList(dataHandler.addingFile(request, userInfo));
        } else if (request.getMessage().getLocation() != null) {
            responseData.messageDataList(unknownTextAction(request,userInfo, "касса не обслуживает"));

            //responseData.messageDataList(dataHandler.addingGeopoint(request, userInfo));
        } else responseData.messageDataList(textCommand(request, userInfo));

        return responseData.build();
    }

    private List<RespMessageData> textCommand(RequestData request, UserInfo userInfo) {
        String messageText = request.getMessage().getText();

        if (messageText.equals(MessageConstants.UPDATE_COMMAND)) {
            return updateMenu(request, userInfo);
        }

        return switch (messageText) {
            case MessageConstants.NEW_USER -> newUserAction(request);
            case MessageConstants.QUEST_COMMAND -> questsMenu(request, userInfo);
            case MessageConstants.ACCOUNT_COMMAND -> accountMenu(request, userInfo);


            default -> unknownTextAction(request, userInfo, messageText);
        };
    }


    /**
     * Получение последнего ответа, который был. Нужен ли вообще этот метод ?
     *
     * @param request
     * @param userInfo
     * @return
     */
    private List<RespMessageData> updateMenu(RequestData request, UserInfo userInfo) {
        return lastUserResponseService.getLastServiceResponseToUser(request, userInfo.getStatus());
    }

    // Показ меню квестов
    private List<RespMessageData> questsMenu(RequestData request, UserInfo userInfo) {
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setReplyMarkup(keyboardsKeeper.getUserQuestMenuKeyboard(userInfo));
        message.setText(MessageConstants.QUEST_MAIN_MENU);
        return List.of(message);
    }

    //показ меню аккаунта
    private List<RespMessageData> accountMenu(RequestData request, UserInfo userInfo) {
        var message = messageBuilder.buildMessage(request, RespMessageType.MESSAGE, RetryType.RETRYABLE);
        message.setReplyMarkup(keyboardsKeeper.getUserAccountMenuKeyboard());
        message.setText(MessageConstants.ACCOUNT_MAIN_MENU);
        return List.of(message);
    }

    /**
     * Отвечает за обработку текста, который может быть ответом пользователя на приглашение.
     * Например просьбу ввести название создаваемого квеста.
     * Определение является ли текст ответом на приглашение происходит по статусу создания CreationStatus
     */
    private List<RespMessageData> unknownTextAction(RequestData request, UserInfo userInfo, String messageText) {
        log.warn("CreatorMessageHandler: неожиданная команда: [{}] от юзера: {}", messageText, userInfo);
        return List.of(messageBuilder.buildUnexpectedMessage(request));
    }

    private List<RespMessageData> newUserAction(RequestData request) {
        return List.of(messageBuilder.buildUnexpectedMessage(request));
    }


}
