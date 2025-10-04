package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class FilePayload {

    private PayloadType payloadType; // для определения, как потом отправлять файл
    private String fileId;           // уникальный идентификатор файла в боте
}
