package ru.zinoviev.quest.request.handler.domain.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Builder
@Getter
@Setter
@ToString
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private Long questUserId;
    private UserRole role;
    private String path;


//    //Инфа о процессе создания квеста
//    private CreationInfo creationInfo;
//
//
//    //инфа о количестве имеющихся квестов
//    private UserQuestsInfo userQuestsInfo;
}
