package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.transport.request.dto.PayloadType;

@Getter
@Builder
@ToString
public class PayloadResponse {

    private PayloadType payloadType; // для определения, как потом отправлять файл
    private String fileId;           // уникальный идентификатор файла в боте
}
