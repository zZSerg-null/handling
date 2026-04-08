package ru.zinoviev.quest.request.handler.domain.action.admin;

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
public class AdminMessageBasicActionHandler extends BasicActionHandler {

    public AdminMessageBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof MessageRequest messageRequest)) {
            throw new ClassCastException("Expected message class but got " + request.getClass());
        }

        System.out.println(AnsiConsole.colorize("AdminMessageActionDispatcher", AnsiConsole.BrightColor.YELLOW));

        if (messageRequest.getText().toLowerCase().startsWith(AdminTextCommand.AI_CHAT)) {
            routeMessageToAI(messageRequest);
        } else {
            anyMessage(messageRequest);
        }
    }

    private void anyMessage(MessageRequest messageRequest) {
        System.out.println("admin: anyMessage");

        if (messageRequest.getPayloadObject() != null) {
            handlePayload(messageRequest);
        } else {
            handleText(messageRequest);
        }
    }

    private void handlePayload(MessageRequest messageRequest) {
        sendMessageResponse(messageRequest, MessageDefinition.TEST_REPLY);
    }

    private void handleText(MessageRequest messageRequest) {
        sendMessageResponse(messageRequest, MessageDefinition.TEST_REPLY);
    }

    private void routeMessageToAI(MessageRequest messageRequest) {
        //
    }


    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.ADMIN;
    }
}
