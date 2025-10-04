package ru.zinoviev.quest.request.handler.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

@Getter
@ToString
public final class WebAppRequest extends RequestData {

    private final String webAppData;

    @Builder
    public WebAppRequest(Long telegramId, String userName, Integer messageId, String webAppData) {
        super(telegramId, userName, messageId);
        this.webAppData = webAppData;
    }

    @Override
    public RequestType getType() {
        return RequestType.WEBAPP;
    }
}
