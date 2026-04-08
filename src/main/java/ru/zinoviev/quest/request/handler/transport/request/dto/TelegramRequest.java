package ru.zinoviev.quest.request.handler.transport.request.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "@type",
        defaultImpl = UnknownTelegramTypeRequest.class
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
public sealed class TelegramRequest permits TelegramCallback, TelegramLocation, TelegramMessage, TelegramPoll, TelegramPollAnswer, TelegramWebApp, UnknownTelegramTypeRequest {

    @NotNull
    private final Long telegramId;

    @NotNull
    private final String userName;
    private final Integer messageId;
}
