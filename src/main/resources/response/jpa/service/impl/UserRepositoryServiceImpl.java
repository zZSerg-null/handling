package ru.zinoviev.quest.request.handler.jpa.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.jpa.entity.QuestUserJPA;
import ru.zinoviev.quest.request.handler.jpa.repository.UserRepository;
import ru.zinoviev.quest.request.handler.jpa.service.UserRepositoryService;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserInfo getUserInfo(Long telegramId, String nickName) {
        QuestUserJPA user = userRepository.findByTelegramId(telegramId)
                .orElseGet(() -> QuestUserJPA.builder()
                        .telegramId(telegramId)
                        .userRole(UserRole.USER)
                        .nickname(nickName)
                        .registrationDate(LocalDate.now())
                        .build());
        return null;
    }

    private QuestUserJPA getUserById(Long userId) {
        Optional<QuestUserJPA> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Пользователь не найден, хотя должен был");
        }
        return optionalUser.get();
    }

}
