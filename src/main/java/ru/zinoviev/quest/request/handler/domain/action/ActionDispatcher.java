package ru.zinoviev.quest.request.handler.domain.action;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.EditMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseKeyboard;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.ResponseFactory;
import ru.zinoviev.quest.request.handler.domain.enums.MenuDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public abstract class ActionDispatcher {


    protected final ResponseFactory responseFactory;
    private final ResponsePublisher publisher;

    private final KeyboardRegistry keyboardRegistry;
    private final MessageRegistry messageRegistry;

    public ActionDispatcher(ResponseFactory responseFactory, ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        this.responseFactory = responseFactory;
        this.publisher = publisher;
        this.keyboardRegistry = keyboardRegistry;
        this.messageRegistry = messageRegistry;
    }

    public abstract void dispatch(RequestData request);

    public abstract DispatchKey key();

    public abstract UserRole getRole();

    protected void sendResponse(ResponseData responseData) {
        publisher.sendResponse(responseData);
    }

    protected ResponseKeyboard getKeyboard(String name){
        return ResponseKeyboard.builder()
                .buttons(keyboardRegistry.getKeyboard(name))
                .keyboardType(keyboardRegistry.getKeyboardType(name))
                .build();
    };

    public void back(RequestData requestData) {
     //   back();
//        requestData.getPath().
    }

    protected String getMessage(String messageName) {
       return messageRegistry.getMessage(messageName);
    }

    protected ResponseData getDefaultSendMessageResponse(RequestData requestData, MenuDefinition definition){
        return SendMessageData.builder()
                .userId(requestData.getTelegramId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(definition.getKeyboard(keyboardRegistry))
                .build();
    }

    protected ResponseData getDefaultEditMessageResponse(RequestData requestData, MenuDefinition definition){
        return EditMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(definition.getKeyboard(keyboardRegistry))
                .build();
    }

    public void ignore(RequestData requestData) {
        System.out.println("Ignored: "+requestData);
    }
}
