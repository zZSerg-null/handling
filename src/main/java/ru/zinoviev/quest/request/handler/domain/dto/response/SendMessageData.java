package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class SendMessageData extends ResponseData {

    private final String message;
    private final Keyboard keyboard;
    private final PayloadResponse payloadResponse;

    @Builder
    public SendMessageData(Long userId, String message, Keyboard keyboard, PayloadResponse payloadResponse) {
        super(userId);
        this.message = message;
        this.keyboard = keyboard;
        this.payloadResponse = payloadResponse;
    }
}
