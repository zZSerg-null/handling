package ru.zinoviev.quest.request.handler.jpa.service;

import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

public interface UserRepositoryService {

    UserInfo getUserInfo(Long userId, String nickName);

    UserRole getUserRole(Long userId);

}
