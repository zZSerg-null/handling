package ru.old.domain.handlers.roles.impl.player;

import lombok.extern.slf4j.Slf4j;
import ru.old.domain.handlers.CallbackHandler;
import old.domain.handlers.h1.BasicMessageBuilder;
import ru.old.domain.handlers.roles.impl.creator.utils.CreatorMessageTextSelector;
import org.springframework.stereotype.Service;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import ru.old.domain.handlers.roles.impl.BasicHandler;

@Slf4j
@Service("playerCallbackHandler")
public class PlayerCallbackHandler extends BasicHandler implements CallbackHandler {

    public PlayerCallbackHandler(BasicMessageBuilder messageBuilder, CreatorMessageTextSelector textSelector) {
        super(messageBuilder, textSelector);
    }

    @Override
    public QuestResponseData processCallback(RequestData request, UserInfo userInfo) {
        System.err.println("playerCallbackHandler");
        return null;
    }
}
