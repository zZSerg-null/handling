package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public final class TelegramWebApp extends TelegramRequest {

    private final String webAppData;

    @Builder
    public TelegramWebApp(Long userId, String userName, Integer messageId, String webAppData) {
        super(userId, userName, messageId);
        this.webAppData = webAppData;
    }
}
