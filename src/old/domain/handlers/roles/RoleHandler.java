package ru.old.domain.handlers.roles;

import old.domain.dto.response.QuestResponseData;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;

public interface RoleHandler {

    QuestResponseData getResponse(RequestData request, UserInfo userInfo);
}
