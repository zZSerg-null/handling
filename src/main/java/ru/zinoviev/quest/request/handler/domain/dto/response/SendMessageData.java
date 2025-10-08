package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class SendMessageData extends ResponseData {

    private final String message;
    private final ResponseKeyboard responseKeyboard;
    private final PayloadResponse payloadResponse;

    @Builder
    public SendMessageData(Long userId, String message, ResponseKeyboard responseKeyboard, PayloadResponse payloadResponse) {
        super(userId);
        this.message = message;
        this.responseKeyboard = responseKeyboard;
        this.payloadResponse = payloadResponse;
    }
}
