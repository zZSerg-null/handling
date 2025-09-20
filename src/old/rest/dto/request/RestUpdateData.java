package ru.old.rest.dto.request;

import lombok.*;
import old.rest.dto.request.message.RestUser;

import java.time.LocalDateTime;


/**
 * Содержит данные Telegram Update, присланные пользователем для обработки
 * Так как в случае недоступности сервиса обработки данные отправленные пользователем могут теряться,
 * они записываются в базу и хранятся там до тех пор пока не будут успешно отправлены.
 */
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RestUpdateData {
    private LocalDateTime receiveTime;
    private RestUser from;
    private RestMessage message;
    private RestCallbackQuery callbackQuery;
    private RestPollAnswer pollAnswer;
}
