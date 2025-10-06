package ru.zinoviev.quest.request.handler.domain.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;
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
    @Cacheable(cacheNames = "info", key = "#telegramId")
    public UserInfo getOrCreateUserInfo(Long telegramId, String userName) {
        var userOptional = getUserByTelegramId(telegramId);

        UserInfo info;

        if (userOptional.isEmpty()){
            BotUser user = createUser(telegramId, userName);
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

            if (!user.getNickname().equals(userName)){
                actualizeUsername(user, userName);
            }
        }

        return info;
    }

    @Override
    @Transactional
    public void setPath(Long telegramId, String postfix) {
        var userOptional = getUserByTelegramId(telegramId);
        if (userOptional.isEmpty()){
            throw  new IllegalArgumentException("setPath: Пользователь "+telegramId+" не найден");
        }

        BotUser user = userOptional.get();

        if (postfix.equals("<-")) {
            user.setPath(user.getPath() + postfix + "/"); // TODO
        } else {
            user.setPath(user.getPath() + postfix + "/");
        }

        userRepository.save(user);
    }

    private void actualizeUsername(BotUser user, String newName) {
        user.setNickname(newName);
        userRepository.save(user);
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
