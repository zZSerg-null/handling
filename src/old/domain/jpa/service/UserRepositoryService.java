package ru.old.domain.jpa.service;

import old.domain.dto.UserInfo;

public interface UserRepositoryService {

    UserInfo getUserInfo(Long userId, String nickName);

    void startQuestCreation(UserInfo userInfo);

    void stopQuestCreation(UserInfo userInfo);

}
