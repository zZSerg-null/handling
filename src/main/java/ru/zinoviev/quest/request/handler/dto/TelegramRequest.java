package ru.zinoviev.quest.request.handler.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TelegramCallback.class, name = "callback"),
        @JsonSubTypes.Type(value = TelegramLocation.class, name = "location"),
        @JsonSubTypes.Type(value = TelegramMessage.class, name = "message"),
        @JsonSubTypes.Type(value = TelegramPoll.class, name = "poll"),
        @JsonSubTypes.Type(value = TelegramPollAnswer.class, name = "poll_answer"),
})
@Getter
@RequiredArgsConstructor
public sealed class TelegramRequest permits TelegramCallback, TelegramLocation, TelegramMessage, TelegramPoll, TelegramPollAnswer {

    private final Long userId;
    private final String userName;
    private final String firstName;
    private final String lastName;

    private final Integer messageId;
}




