package ru.zinoviev.quest.request.handler.domain.action;

import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.dto.internal.QuestInfo;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.List;


public abstract class BasicActionHandler {

    private final ResponsePublisher publisher;
    private final TelegramMessageBuilder messageBuilder;

    public BasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        this.publisher = publisher;
        this.messageBuilder = messageBuilder;
    }

    public abstract void dispatch(RequestData request);

    public abstract DispatchKey key();

    public abstract BotUserRole getRole();

    protected void sendDeleteMessageResponse(RequestData requestData) {
        publisher.sendResponse(messageBuilder.getDeleteMessageResponse(requestData));
    }

    protected void sendMessageResponse(RequestData requestData, MessageDefinition definition){
        publisher.sendResponse(messageBuilder.getSendMessageResponse(requestData, definition));
    }

    protected void sendEditMessageResponse(RequestData requestData, MessageDefinition definition){
        publisher.sendResponse(messageBuilder.getEditMessageResponse(requestData, definition));
    }

    protected void sendEditMessageResponse(RequestData requestData, MessageDefinition definition, List<QuestInfo> buttons){
        publisher.sendResponse(messageBuilder.getEditMessageResponse(requestData, definition, buttons));
    }

    public void ignore(RequestData requestData) {
        sendDeleteMessageResponse(requestData);
    }
}
