package ru.zinoviev.quest.request.handler.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

@Getter
@ToString
public final class CallbackRequest extends RequestData {

    private final String data;

    @Builder
    public CallbackRequest(Long telegramId, String userName, String data) {
        super(telegramId, userName);
        this.data = data;
    }

    @Override
    public RequestType getType() {
        return RequestType.CALLBACK;
    }

}
