package ru.zinoviev.quest.request.handler.domain.jpa.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.domain.jpa.BotUser;
import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;
import ru.zinoviev.quest.request.handler.domain.jpa.repo.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;
    private final CacheManager cacheManager;
    private final PlatformTransactionManager transactionManager;


    @Override
    public UserInfo getOrCreateUserInfo(Long telegramId, String userName) {
        Cache cache = cacheManager.getCache("info");
        UserInfo cacheInfo = cache.get(telegramId, UserInfo.class);

        //noinspection ConstantConditions
        if (cacheInfo != null) {
            return cacheInfo;
        }

        UserInfo info = new TransactionTemplate(transactionManager).execute(status -> {
            Optional<BotUser> userOpt = userRepository.findBotUserByTelegramId(telegramId);

            if (userOpt.isPresent()) {
                BotUser user = userOpt.get();

                if (!user.getNickname().equals(userName)) {
                    user.setNickname(userName);
                }
                return buildUserInfo(user);
            }

            BotUser newUser = createUser(telegramId, userName);
            return buildUserInfo(newUser);
        });

        cache.put(telegramId, info);
        return info;
    }

    @Override
    @Transactional
    // TODO метод не верно написан (пока не используется)
    public void setPath(Long userId, String postfix) {
        BotUser user = getUserById(userId);

        if (postfix.equals("<-")) {
            user.setPath(user.getPath() + postfix + "/");
        } else {
            user.setPath(user.getPath() + postfix + "/");
        }

        userRepository.save(user);
    }



    @Override
    @Transactional
    public void setRole(Long userId, UserRole newRole) {
        BotUser user = getUserById(userId);
        user.setUserRole(newRole);

        Cache cache = cacheManager.getCache("info");
        cache.evict(user.getTelegramId());
        cache.put(user.getTelegramId(), buildUserInfo(user));
    }

    private UserInfo buildUserInfo(BotUser user) {
        return UserInfo.builder()
                .questUserId(user.getId())
                .path(user.getPath())
                .role(user.getUserRole())
                .build();
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

    private BotUser getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("setPath: Пользователь " + userId + " не найден"));
    }

}
