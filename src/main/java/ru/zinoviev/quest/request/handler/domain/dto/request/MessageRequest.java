package ru.zinoviev.quest.request.handler.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

@Getter
@ToString
public final class MessageRequest extends RequestData {

    private final String text;
    private final PayloadObject payloadObject;

    @Builder
    public MessageRequest(Long telegramId, String userName, String text, PayloadObject payloadObject) {
        super(telegramId, userName);
        this.text = text;
        this.payloadObject = payloadObject;
    }

    @Override
    public RequestType getType() {
        return RequestType.MESSAGE;
    }
}
