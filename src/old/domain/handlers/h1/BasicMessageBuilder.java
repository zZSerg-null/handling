package ru.old.domain.handlers.h1;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import old.domain.dto.request.RequestData;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.RespMessageType;
import old.domain.enums.RetryType;

@Component
@RequiredArgsConstructor
public abstract class BasicMessageBuilder {

    public RespMessageData buildMessage(RequestData requestData, RespMessageType messageType, RetryType retryType) {
        Integer messageId = requestData.getCallbackData() != null ? requestData.getCallbackData().getMessageId() : requestData.getMessage().getMessageId();
        return RespMessageData.builder()
                .messageType(messageType)
                .retryType(retryType)
                .messageId(messageId)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }

    protected RespMessageData buildDefaultMessage(RequestData requestData){
        Integer messageId = requestData.getCallbackData() != null ? requestData.getCallbackData().getMessageId() : requestData.getMessage().getMessageId();

        return RespMessageData.builder()
                .messageType(RespMessageType.MESSAGE)
                .retryType(RetryType.IGNORE)
                .messageId(messageId)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }



    protected RespMessageData buildDefaultEditMessage(RequestData requestData){
        Integer messageId = requestData.getCallbackData() != null ? requestData.getCallbackData().getMessageId() : requestData.getMessage().getMessageId();

        return RespMessageData.builder()
                .messageType(RespMessageType.EDIT_MESSAGE)
                .retryType(RetryType.IGNORE)
                .messageId(messageId)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }

    protected RespMessageData buildDefaultDeleteMessage(RequestData requestData){
        Integer messageId = requestData.getCallbackData() != null ? requestData.getCallbackData().getMessageId() : requestData.getMessage().getMessageId();

        return RespMessageData.builder()
                .messageType(RespMessageType.DELETE_MESSAGE)
                .retryType(RetryType.IGNORE)
                .messageId(messageId)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }


    protected RespMessageData buildDefaultMediaGroupMessage(RequestData requestData){
        Integer messageId = requestData.getCallbackData() != null ? requestData.getCallbackData().getMessageId() : requestData.getMessage().getMessageId();

        return RespMessageData.builder()
                .messageType(RespMessageType.MEDIA_GROUP)
                .retryType(RetryType.IGNORE)
                .messageId(messageId)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }

    public RespMessageData buildUnexpectedMessage(RequestData requestData) {
        return RespMessageData.builder()
                .messageType(RespMessageType.MESSAGE)
                .text(requestData.getCallbackData() != null ? "была нажат кнопка которая давно неактуальна" : "Не знаю, что с этим делать")
                .retryType(RetryType.IGNORE)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }

    protected RespMessageData buildHTMLPageMessage(RequestData requestData, RespMessageType messageType) {
        Integer messageId = requestData.getMessage().getMessageId();

        return RespMessageData.builder()
                .messageType(messageType)
                .retryType(RetryType.RETRYABLE)
                .parseMode("HTML")
                .messageId(messageId)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }
}
