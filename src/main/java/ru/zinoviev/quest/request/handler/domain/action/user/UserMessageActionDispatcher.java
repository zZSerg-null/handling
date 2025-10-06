package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.ResponseFactory;
import ru.zinoviev.quest.request.handler.domain.enums.MenuDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class UserMessageActionDispatcher extends ActionDispatcher {

    public UserMessageActionDispatcher(ResponseFactory responseFactory, ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(responseFactory, publisher, keyboardRegistry, messageRegistry);
    }

    public void dispatch(RequestData request) {
        MessageRequest messageRequest = (MessageRequest) request;
        System.out.println("UserMessageActionDispatcher");

        if (messageRequest.getText() != null) {
            textHandling(messageRequest);
        } else if (messageRequest.getPayloadObject() != null) {
            payloadHandling(messageRequest);
        }
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public UserRole getRole() {
        return UserRole.USER;
    }

    private void textHandling(MessageRequest messageRequest) {
        if (messageRequest.getText().toLowerCase().equals(UserTextCommand.START)) {
            sendHello(messageRequest);
        } else {
            anyMessage(messageRequest);
        }
    }

    //TODO формирование сообщения,
    // формирование выделения. О динамике речи нет, у нас пока статика.
    // Но если будет подключаться нейросеть для ответов то
    private void anyMessage(MessageRequest messageRequest) {
        String response1 = "✅ *Команды бота:* \n\n" +
                "📋 /start: - показывает основное меню" + "\n" +
                "*bold*\n" +
                "_italic_\n" +
                "[inline URL](http://www.example.com/)\n" +
                "`inline fixed-width code` " + "\n";

        sendResponse(getDefaultSendMessageResponse(messageRequest, MenuDefinition.USER_MAIN_MENU));
    }

    private void sendHello(MessageRequest messageRequest) {
        sendResponse(getDefaultSendMessageResponse(messageRequest, MenuDefinition.USER_MAIN_MENU));
    }

    private void payloadHandling(MessageRequest messageRequest) {
        System.out.println("PAYLOAD");
        System.out.println(messageRequest.getPayloadObject());
    }

    private void sendMainMenu(MessageRequest messageRequest) {
        sendResponse(SendMessageData.builder().build());
    }

    private void text(MessageRequest messageRequest) {

    }

}
