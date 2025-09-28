package ru.zinoviev.quest.request.handler.domain.jpa.service;

import ru.zinoviev.quest.request.handler.domain.UserInfo;

public interface UserRepositoryService {

    UserInfo getOrCreateUserInfo(Long userId, String userName);

}
