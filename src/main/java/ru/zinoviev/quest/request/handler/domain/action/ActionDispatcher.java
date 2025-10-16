package ru.zinoviev.quest.request.handler.domain.action;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.CallbackRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.*;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.domain.jpa.QuestInfo;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.List;

@Component
public abstract class ActionDispatcher {

    private final ResponsePublisher publisher;
    private final KeyboardRegistry keyboardRegistry;
    private final MessageRegistry messageRegistry;

    public ActionDispatcher(ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        this.publisher = publisher;
        this.keyboardRegistry = keyboardRegistry;
        this.messageRegistry = messageRegistry;
    }

    public abstract void dispatch(RequestData request);

    public abstract DispatchKey key();

    public abstract UserRole getRole();

    protected void back(CallbackRequest callbackRequest){
        System.out.println("BACK");
    };


    protected void sendResponse(ResponseData responseData) {
        publisher.sendResponse(responseData);
    }

    protected ResponseData getDeleteMessageResponse(RequestData requestData) {
        return DeleteMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .build();
    }

    protected ResponseData getSendMessageResponse(RequestData requestData, MessageDefinition definition){
        return SendMessageData.builder()
                .userId(requestData.getTelegramId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(definition.getKeyboard(keyboardRegistry))
                .build();
    }

    protected ResponseData getEditMessageResponse(RequestData requestData, MessageDefinition definition){
        return EditMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(definition.getKeyboard(keyboardRegistry))
                .build();
    }

    protected ResponseData getEditMessageResponse(RequestData requestData, MessageDefinition definition, List<QuestInfo> buttons){
        return EditMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(keyboardRegistry.buildKeyboard(buttons, definition))
                .build();
    }

    public void ignore(RequestData requestData) {
        sendResponse(getDeleteMessageResponse(requestData));
    }
}
