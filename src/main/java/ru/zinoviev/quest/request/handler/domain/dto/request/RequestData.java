package ru.zinoviev.quest.request.handler.domain.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

@Getter
@RequiredArgsConstructor
public abstract sealed class RequestData
        permits MessageRequest, CallbackRequest, LocationRequest, PollRequest, PollAnswerRequest, WebAppRequest {

    private final Long telegramId;
    private final String userName;
    private final Integer messageId;

    @Setter
    private String path;

    public abstract RequestType getType();
}
