package ru.old.domain.handlers.roles;

import old.domain.dto.response.QuestResponseData;
import ru.old.domain.handlers.CallbackHandler;
import ru.old.domain.handlers.MessageHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;

@Component
public class AdminRoleHandler implements RoleHandler {

    private final MessageHandler adminMessageHandler;
    private final CallbackHandler adminCallbackHandler;

    public AdminRoleHandler(@Qualifier("adminMessageHandler") MessageHandler adminMessageHandler,
                            @Qualifier("adminCallbackHandler") CallbackHandler adminCallbackHandler) {
        this.adminMessageHandler = adminMessageHandler;
        this.adminCallbackHandler = adminCallbackHandler;
    }

    @Override
    public QuestResponseData getResponse(RequestData request, UserInfo userInfo) {
        return null;
    }
}