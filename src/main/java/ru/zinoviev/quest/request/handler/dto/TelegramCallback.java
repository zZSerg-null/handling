package ru.zinoviev.quest.request.handler.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class TelegramCallback extends TelegramRequest {

    private final String data;

    @Builder
    public TelegramCallback(Long userId, String userName, String firstName, String lastName, Integer messageId,
                            String data) {

        super(userId, userName, firstName, lastName, messageId);
        this.data = data;
    }
}
