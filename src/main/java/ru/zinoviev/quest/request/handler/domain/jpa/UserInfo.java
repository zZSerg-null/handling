package ru.zinoviev.quest.request.handler.domain.jpa;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
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
