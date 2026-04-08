package ru.zinoviev.quest.request.handler.domain.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class UnexpectedRequestTypeDispatcher extends BasicActionHandler {

    @Value("${dispatching.unknown-request:IGNORING}")
    private String reactionType;

    public UnexpectedRequestTypeDispatcher(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    @Override
    public void dispatch(RequestData request) {
        AnsiConsole.println("Неожиданный тип сообщения или роль пользователя: " + request, AnsiConsole.BrightColor.CYAN);

        if (reactionType.equalsIgnoreCase("EXCEPTION")) {
            throw new UnhandableRequestTypeException("Событие не может быть обработано");

        } else if (reactionType.equalsIgnoreCase("SEND_MESSAGE")) {
            sendMessageResponse(request, MessageDefinition.TEST_REPLY);
        }
    }

    @Override
    public DispatchKey key() {
        return null;
    }

    @Override
    public BotUserRole getRole() {
        return null;
    }


}
