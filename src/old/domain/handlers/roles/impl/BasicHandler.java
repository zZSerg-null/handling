package ru.old.domain.handlers.roles.impl;

import old.domain.handlers.h1.BasicMessageBuilder;
import old.domain.handlers.roles.impl.creator.utils.CreatorMessageTextSelector;
import ru.old.domain.dto.UserInfo;
import ru.old.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.response.QuestResponseData;

public interface BasicHandler {
    public QuestResponseData handle(RequestData request, UserInfo userInfo);
}
