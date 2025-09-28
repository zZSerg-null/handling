package ru.old.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.old.domain.enums.UserRole;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@ToString
public class UserInfo {

    private Long questUserId;
    private String nickName;
    private LocalDate registrationDate;

    /**
     * используется для определения роли пользователя для последующей обработки.
     * AUTHOR - роль по умолчанию. Может заниматься созданием квестов и управлением аккаунтом
     * PLAYER - роль, получаемая при входе в игру. Взаимодействует с запущенным квестом
     * ADMIN - мастер игры. Может как принимать участие в игре так и совершать управленческие действия в отношении
     *         как самого квеста так и игроков
     */
    private UserRole status;

    //Инфа о процессе создания квеста
    private CreationInfo creationInfo;


    //инфа о количестве имеющихся квестов
    private UserQuestsInfo userQuestsInfo;
}
