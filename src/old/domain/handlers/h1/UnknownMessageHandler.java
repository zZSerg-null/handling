package ru.old.domain.handlers.h1;

import org.springframework.stereotype.Component;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;

@Component
public class UnknownMessageHandler implements BasicHandler {

    @Override
    public QuestResponseData handle(RequestData request, UserInfo userInfo) {
        return null;
    }
}
