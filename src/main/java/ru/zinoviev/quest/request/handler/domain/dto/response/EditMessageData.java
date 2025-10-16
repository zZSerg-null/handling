package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class EditMessageData extends ResponseData {

    private final String message;
    private final Integer messageId;
    private final ResponseKeyboard responseKeyboard;

    @Builder
    public EditMessageData(Long userId, String message, Integer messageId, ResponseKeyboard responseKeyboard) {
        super(userId);
        this.message = message;
        this.messageId = messageId;
        this.responseKeyboard = responseKeyboard;
    }
}
