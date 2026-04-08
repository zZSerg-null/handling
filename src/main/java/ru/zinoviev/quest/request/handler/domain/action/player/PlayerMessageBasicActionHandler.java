package ru.zinoviev.quest.request.handler.domain.action.player;

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
public class PlayerMessageBasicActionHandler extends BasicActionHandler {

    public PlayerMessageBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof MessageRequest messageRequest)) {
            throw new ClassCastException("Expected message class but got " + request.getClass());
        }


        System.out.println(AnsiConsole.colorize("PlayerMessageActionDispatcher", AnsiConsole.BrightColor.YELLOW));
        sendMessageResponse(messageRequest, MessageDefinition.TEST_REPLY);
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.PLAYER;
    }

}
