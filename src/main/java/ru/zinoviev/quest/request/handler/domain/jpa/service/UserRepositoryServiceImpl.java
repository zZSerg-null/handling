package ru.zinoviev.quest.request.handler.domain.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zinoviev.quest.request.handler.domain.UserInfo;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.domain.jpa.BotUser;
import ru.zinoviev.quest.request.handler.domain.jpa.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    @Cacheable(cacheNames = "info", key = "#userId")
    public UserInfo getOrCreateUserInfo(Long userId, String userName) {
        var userOptional = getUserByTelegramId(userId);

        UserInfo info;

        if (userOptional.isEmpty()){
            BotUser user = createUser(userId, userName);
            info = UserInfo.builder()
                    .questUserId(user.getId())
                    .path(user.getPath())
                    .role(user.getUserRole())
                    .build();
        } else {
            BotUser user = userOptional.get();
            info = UserInfo.builder()
                    .questUserId(user.getId())
                    .path(user.getPath())
                    .role(user.getUserRole())
                    .build();
        }

        return info;
    }

    private BotUser createUser(Long userId, String userName) {
        return userRepository.save(
                BotUser.builder()
                        .telegramId(userId)
                        .nickname(userName)
                        .userRole(UserRole.USER)
                        .path("/")
                        .build()
        );
    }

    private Optional<BotUser> getUserByTelegramId(Long telegramId){
        return userRepository.findBotUserByTelegramId(telegramId);
    }
}
