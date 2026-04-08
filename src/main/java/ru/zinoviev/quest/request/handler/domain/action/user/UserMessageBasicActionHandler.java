package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class UserMessageBasicActionHandler extends BasicActionHandler {

    public UserMessageBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof MessageRequest messageRequest)) {
            throw new ClassCastException("Expected message class but got " + request.getClass());
        }

        System.out.println(AnsiConsole.colorize("UserMessageActionDispatcher", AnsiConsole.BrightColor.YELLOW));

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
    public BotUserRole getRole() {
        return BotUserRole.USER;
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

        sendMessageResponse(messageRequest, MessageDefinition.USER_MAIN_MENU);
    }

    private void sendHello(MessageRequest messageRequest) {
        sendMessageResponse(messageRequest, MessageDefinition.USER_MAIN_MENU);
    }

    private void payloadHandling(MessageRequest messageRequest) {
        System.out.println("PAYLOAD");
        System.out.println(messageRequest.getPayloadObject());
        sendMessageResponse(messageRequest, MessageDefinition.TEST_REPLY);
    }

}
