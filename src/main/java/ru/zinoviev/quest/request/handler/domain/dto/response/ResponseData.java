package ru.zinoviev.quest.request.handler.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "@type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SendMessageData.class, name = "send_message"),
        @JsonSubTypes.Type(value = EditMessageData.class, name = "edit_message"),
        @JsonSubTypes.Type(value = DeleteMessageData.class, name = "delete_message")
})
@Getter
@ToString
@RequiredArgsConstructor
public sealed class ResponseData permits SendMessageData, EditMessageData, DeleteMessageData {

    private final Long chatId;

}
