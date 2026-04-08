package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;

public final class UnknownTelegramTypeRequest extends TelegramRequest{

    @Builder
    public UnknownTelegramTypeRequest(Long telegramId, String userName, Integer messageId) {
        super(telegramId, userName, messageId);
    }
}
