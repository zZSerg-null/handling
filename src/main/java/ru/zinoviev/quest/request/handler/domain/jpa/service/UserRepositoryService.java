package ru.zinoviev.quest.request.handler.domain.jpa.service;

import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;

public interface UserRepositoryService {

    UserInfo getOrCreateUserInfo(Long telegramId, String userName);

    void setPath(Long telegramId, String postfix);
}
