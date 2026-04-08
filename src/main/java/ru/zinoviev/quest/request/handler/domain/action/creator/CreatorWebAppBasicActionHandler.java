package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.internal.WebAppRequest;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorWebAppBasicActionHandler extends BasicActionHandler {

    public CreatorWebAppBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof WebAppRequest webAppRequest)) {
            throw new ClassCastException("Expected webApp class but got " + request.getClass());
        }


        System.out.println(AnsiConsole.colorize("CreatorWebAppActionDispatcher", AnsiConsole.BrightColor.YELLOW));
        sendMessageResponse(webAppRequest, MessageDefinition.TEST_REPLY);
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.WEBAPP);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.CREATOR;
    }

}
