package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.*;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    private Long userId;
    private Long telegramId;
    private String userName;
    private BotUserRole role;


//    //Инфа о процессе создания квеста
//    private CreationInfo creationInfo;
//
//
//    //инфа о количестве имеющихся квестов
//    private UserQuestsInfo userQuestsInfo;
}
