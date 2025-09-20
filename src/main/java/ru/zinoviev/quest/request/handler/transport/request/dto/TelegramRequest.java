package ru.zinoviev.quest.request.handler.transport.request.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TelegramCallback.class, name = "callback"),
        @JsonSubTypes.Type(value = TelegramLocation.class, name = "location"),
        @JsonSubTypes.Type(value = TelegramMessage.class, name = "message"),
        @JsonSubTypes.Type(value = TelegramPoll.class, name = "poll"),
        @JsonSubTypes.Type(value = TelegramPollAnswer.class, name = "poll_answer"),
        @JsonSubTypes.Type(value = TelegramWebApp.class, name = "webapp")
})
@Getter
@RequiredArgsConstructor
public sealed class TelegramRequest permits TelegramCallback, TelegramLocation, TelegramMessage, TelegramPoll, TelegramPollAnswer, TelegramWebApp {

    private final Long userId;
    private final String userName;
    private final Integer messageId;
}
