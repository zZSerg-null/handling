package ru.old.domain.jpa.service.impl;

import lombok.RequiredArgsConstructor;
import old.domain.dto.CreationInfo;
import old.domain.dto.UserQuestsInfo;
import ru.old.domain.enums.UserRole;
import old.domain.jpa.entity.QuestJPA;
import old.domain.jpa.entity.QuestUserJPA;
import ru.old.domain.jpa.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import old.domain.dto.UserInfo;
import ru.old.domain.jpa.service.CreationInfoService;
import ru.old.domain.jpa.service.QuestRepositoryService;
import ru.old.domain.jpa.service.UserRepositoryService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private final UserRepository userRepository;

    private final QuestRepositoryService questRepositoryService;
    private final CreationInfoService creationInfoService;

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

        if (user.getId() == null){
            userRepository.save(user);
            System.out.println("save user: " + user);

            return UserInfo.builder()
                    .status(UserRole.USER)
                    .questUserId(-1L)
                    .build();
        }



        //находим список квестов пользователя (необходимо для определения показывать кнопку "Мои квесты" или нет)
        List<String> nameList = questRepositoryService.getUserQuestNameList(user.getTelegramId());
        int runningQuestCount = 0;
        UserQuestsInfo userQuestsInfo = UserQuestsInfo.builder()
                .questNames(nameList)
                .runningQuestCount(runningQuestCount)
                .build();

        //находим данные о создаваемом квесте
        CreationInfo creationInfo = creationInfoService.getInfoByUserId(user.getId());

        return UserInfo.builder()
                .questUserId(user.getId())
                .status(user.getUserRole())
                .nickName(user.getNickname())
                .registrationDate(user.getRegistrationDate())
                .creationInfo(creationInfo)
                .userQuestsInfo(userQuestsInfo)
                .build();
    }

    @Override
    @Transactional
    public void startQuestCreation(UserInfo userInfo) {
        QuestUserJPA user = getUserById(userInfo.getQuestUserId());
        user.setUserRole(UserRole.CREATOR);
        userRepository.save(user);

        QuestJPA quest = questRepositoryService.createNewQuest(user);
        creationInfoService.create(quest, user);
    }

    @Override
    @Transactional
    public void stopQuestCreation(UserInfo userInfo) {
        QuestUserJPA user = getUserById(userInfo.getQuestUserId());
        user.setUserRole(UserRole.USER);
        userRepository.save(user);
        creationInfoService.deleteInfo(userInfo.getCreationInfo());
    }

    private QuestUserJPA getUserById(Long userId) {
        Optional<QuestUserJPA> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new RuntimeException("Пользователь не найден, хотя должен был");
        }
        return optionalUser.get();
    }

}
