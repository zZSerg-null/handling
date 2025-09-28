package ru.old.domain.handlers.roles;

import old.domain.dto.response.QuestResponseData;
import ru.old.domain.handlers.CallbackHandler;
import ru.old.domain.handlers.MessageHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;

@Component
public class PlayerRoleHandler implements RoleHandler {

    private final MessageHandler playerMessageHandler;
    private final CallbackHandler playerCallbackHandler;

    public PlayerRoleHandler(@Qualifier("creatorMessageHandler") MessageHandler playerMessageHandler,
                             @Qualifier("creatorCallbackHandler") CallbackHandler playerCallbackHandler) {
        this.playerMessageHandler = playerMessageHandler;
        this.playerCallbackHandler = playerCallbackHandler;
    }

    @Override
    public QuestResponseData getResponse(RequestData request, UserInfo userInfo) {
        return null;
    }
}