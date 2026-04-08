package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.PollRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorPollBasicActionHandler extends BasicActionHandler {

    public CreatorPollBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof PollRequest pollRequest)) {
            throw new ClassCastException("Expected poll class but got " + request.getClass());
        }

        System.out.println(AnsiConsole.colorize("CreatorPollActionDispatcher", AnsiConsole.BrightColor.YELLOW));
        sendMessageResponse(pollRequest, MessageDefinition.TEST_REPLY);
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.POLL);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.CREATOR;
    }

}
