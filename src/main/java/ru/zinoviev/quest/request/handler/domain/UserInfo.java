package ru.zinoviev.quest.request.handler.domain;

import lombok.*;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Builder
@Getter
@Setter
@ToString
public class UserInfo {

    private Long questUserId;
    private UserRole role;
    private String path;
//
//    //Инфа о процессе создания квеста
//    private CreationInfo creationInfo;
//
//
//    //инфа о количестве имеющихся квестов
//    private UserQuestsInfo userQuestsInfo;
}
