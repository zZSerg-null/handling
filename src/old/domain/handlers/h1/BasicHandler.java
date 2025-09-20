package ru.old.domain.handlers.h1;

import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;

public interface BasicHandler {

    QuestResponseData handle(RequestData request, UserInfo userInfo);
}
