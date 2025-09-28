package ru.old.domain.handlers.h1.user;

import old.domain.dto.response.QuestResponseData;
import ru.old.domain.handlers.h1.BasicHandler;
import ru.old.domain.handlers.h1.BasicMessageBuilder;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;

public class UserCallbackHandler extends BasicMessageHandler implements BasicHandler {

    public UserCallbackHandler(BasicMessageBuilder responseMessageBuilder) {
        super(responseMessageBuilder);
    }

    @Override
    public QuestResponseData handle(RequestData request, UserInfo userInfo) {
        return null;
    }
}
