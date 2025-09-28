package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class DeleteMessageData extends ResponseData {

    private final Integer messageId;

    @Builder
    public DeleteMessageData(Long userId, Integer messageId) {
        super(userId);
        this.messageId = messageId;
    }
}
