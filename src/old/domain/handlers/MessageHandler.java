package ru.old.domain.handlers;

import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;

public interface MessageHandler {

    QuestResponseData processMessage(RequestData request, UserInfo userInfo);
}
