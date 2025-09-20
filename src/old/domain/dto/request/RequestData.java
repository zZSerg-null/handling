package ru.old.domain.dto.request;

import lombok.*;
import old.domain.dto.request.message.RequestOwner;
import ru.old.domain.enums.RequestMessageType;


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
public class RequestData {
    private RequestOwner from;
    private RequestMessageType messageType;
    private Message message;
    private CallbackData callbackData;

    private PollAnswer pollAnswer;
}
