package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class TelegramMessage extends TelegramRequest {

    private final String text;
    private final FilePayload filePayload;

    @Builder
    public TelegramMessage(Long userId, String userName, Integer messageId, String text, FilePayload filePayload) {

        super(userId, userName, messageId);
        this.text = text;
        this.filePayload = filePayload;
    }
}
