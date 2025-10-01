package ru.zinoviev.quest.request.handler.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

@Getter
@ToString
public final class CallbackRequest extends RequestData {

    private final String callbackData;

    @Builder
    public CallbackRequest(Long telegramId, String userName, Integer messageId, String callbackData) {
        super(telegramId, userName, messageId);
        this.callbackData = callbackData;
    }




    @Override
    public RequestType getType() {
        return RequestType.CALLBACK;
    }

}
