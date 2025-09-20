package ru.old.domain.handlers.roles;

import lombok.extern.slf4j.Slf4j;
import old.domain.dto.response.QuestResponseData;
import ru.old.domain.handlers.CallbackHandler;
import ru.old.domain.handlers.MessageHandler;
import old.domain.handlers.h1.BasicMessageBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;

import java.util.List;

@Slf4j
@Component
public class UserRoleHandler implements RoleHandler {

    private final MessageHandler userMessageHandler;
    private final CallbackHandler userCallbackHandler;
    private final BasicMessageBuilder messageBuilder;

    public UserRoleHandler(@Qualifier("userMessageHandler") MessageHandler userMessageHandler,
                           @Qualifier("userCallbackHandler") CallbackHandler userCallbackHandler,
                           BasicMessageBuilder messageBuilder) {

        this.userMessageHandler = userMessageHandler;
        this.userCallbackHandler = userCallbackHandler;
        this.messageBuilder = messageBuilder;
    }

    /**
     * реализация обработчика запроса пользователя со статусом AUTHOR, то есть дефолтного пользователя,
     * который не принимает участия в игре и способен работать над созданием квестов.
     */
    @Override
    public QuestResponseData getResponse(RequestData request, UserInfo userInfo) {

        if (request.getCallbackData() != null) {
            return callbackHandler(request, userInfo);

        } else if (request.getMessage() != null) {
            return messageHandler(request, userInfo);

        } else {
            log.warn("CreatorStatusHandler: неизвестный тип сообщения: " + request);
            return QuestResponseData.builder()
                    .messageDataList(List.of(messageBuilder.buildUnexpectedMessage(request)))
                    .build();
        }
    }

    private QuestResponseData messageHandler(RequestData request, UserInfo userInfo) {
        return userMessageHandler.processMessage(request, userInfo);
    }

    private QuestResponseData callbackHandler(RequestData request, UserInfo userInfo) {
        return userCallbackHandler.processCallback(request, userInfo);
    }

}
