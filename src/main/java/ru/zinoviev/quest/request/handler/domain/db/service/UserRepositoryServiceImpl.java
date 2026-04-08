package ru.zinoviev.quest.request.handler.domain.db.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.db.entity.BotUser;
import ru.zinoviev.quest.request.handler.domain.dto.internal.UserInfo;
import ru.zinoviev.quest.request.handler.domain.db.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "userInfo", key = "#telegramId")
    public Optional<UserInfo> getUserInfo(Long telegramId, String userName) {
        return userRepository.findBotUserByTelegramId(telegramId)
                .map(this::buildUserInfo);
    }


    @Override
    @CachePut(value = "userInfo", key = "#telegramId")
    public UserInfo updateUser(Long telegramId, String userName) {
        BotUser user = findUserByTelegramId(telegramId);
        user.setNickname(userName);
        return buildUserInfo(user);
    }



    @Override
    public UserInfo createUser(Long telegramId, String userName) {
        BotUser user = userRepository.save(
                BotUser.builder()
                        .telegramId(telegramId)
                        .nickname(userName)
                        .botUserRole(BotUserRole.USER)
                        .path("/")
                        .build());

        return buildUserInfo(user);
    }


    @Override
    @Transactional
    public void setRole(Long telegramId, BotUserRole newRole) {
        BotUser user = findUserByTelegramId(telegramId);
        user.setBotUserRole(newRole);
    }

    private UserInfo buildUserInfo(BotUser user) {
        return UserInfo.builder()
                .userId(user.getId())
                .userName(user.getNickname())
                .telegramId(user.getTelegramId())
                .role(user.getBotUserRole())
                .build();
    }

    private BotUser findUserByTelegramId(Long userId){
        return userRepository.findBotUserByTelegramId(userId)
                .orElseThrow(()-> new RuntimeException("User not found: "+userId));
    }

}
