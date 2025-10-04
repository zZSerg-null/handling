package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class TelegramCallback extends TelegramRequest {

    private final String callbackData;

    @Builder
    public TelegramCallback(Long userId, String userName, Integer messageId, String callbackData) {

        super(userId, userName, messageId);
        this.callbackData = callbackData;
    }
}
