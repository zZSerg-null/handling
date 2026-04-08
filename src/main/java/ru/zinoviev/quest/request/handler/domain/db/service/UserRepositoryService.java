package ru.zinoviev.quest.request.handler.domain.db.service;

import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.dto.internal.UserInfo;

import java.util.Optional;

public interface UserRepositoryService {

    Optional<UserInfo> getUserInfo(Long telegramId, String userName);

    UserInfo createUser(Long telegramId, String userName);

    UserInfo updateUser(Long telegramId, String userName);

    void setRole(Long userId, BotUserRole newRole);
}
