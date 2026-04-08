package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.LocationRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorLocationBasicActionHandler extends BasicActionHandler {

    public CreatorLocationBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof LocationRequest locationRequest)) {
            throw new ClassCastException("Expected location class but got " + request.getClass());
        }


        System.out.println(AnsiConsole.colorize("CreatorLocationActionDispatcher", AnsiConsole.BrightColor.YELLOW));

        sendMessageResponse(locationRequest, MessageDefinition.TEST_REPLY);
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.LOCATION);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.CREATOR;
    }

}
