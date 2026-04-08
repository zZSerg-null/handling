package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.transport.request.dto.TelegramRequest;

@Getter
@ToString
public final class UnexpectedTypeRequest extends RequestData {

    @Builder
    public UnexpectedTypeRequest(Long telegramId, String userName, Integer messageId) {
        super(telegramId, userName, messageId);
    }

    @Override
    public RequestType getType() {
        return null;
    }
}
