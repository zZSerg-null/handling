package ru.zinoviev.quest.request.handler.domain.action.player;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.PollAnswerRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class PLayerPollAnswerBasicActionHandler extends BasicActionHandler {

    public PLayerPollAnswerBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    public void dispatch(RequestData request) {
        if (!(request instanceof PollAnswerRequest pollAnswerRequest)) {
            throw new ClassCastException("Expected pollAnswer class but got " + request.getClass());
        }


        System.out.println(AnsiConsole.colorize("PLayerPollAnswerActionDispatcher", AnsiConsole.BrightColor.YELLOW));
        sendMessageResponse(pollAnswerRequest, MessageDefinition.TEST_REPLY);
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.POLL_ANSWER);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.PLAYER;
    }

}
