package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;

@Getter
@RequiredArgsConstructor
@ToString
public abstract sealed class RequestData
        permits CallbackRequest, LocationRequest, MessageRequest, PollAnswerRequest, PollRequest, WebAppRequest, UnexpectedTypeRequest {

    private final Long telegramId;
    private final String userName;
    private final Integer messageId;

    @Setter
    private String path;
    @Setter
    private BotUserRole role;
    @Setter
    private Long userId;

    public abstract RequestType getType();
}
