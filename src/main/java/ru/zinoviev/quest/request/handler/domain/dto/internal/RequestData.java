package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Getter
@RequiredArgsConstructor
@ToString
public abstract sealed class RequestData
        permits MessageRequest, CallbackRequest, LocationRequest, PollRequest, PollAnswerRequest, WebAppRequest {

    private final Long telegramId;
    private final String userName;
    private final Integer messageId;

    @Setter
    private String path;
    @Setter
    private UserRole role;
    @Setter
    private Long userId;

    public abstract RequestType getType();
}
