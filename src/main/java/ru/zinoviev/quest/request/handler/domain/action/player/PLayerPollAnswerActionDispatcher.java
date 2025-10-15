package ru.zinoviev.quest.request.handler.domain.action.player;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.PollAnswerRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class PLayerPollAnswerActionDispatcher extends ActionDispatcher {

    public PLayerPollAnswerActionDispatcher(ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(publisher, keyboardRegistry, messageRegistry);
    }

    public void dispatch(RequestData request) {
        PollAnswerRequest pollAnswerRequest = (PollAnswerRequest) request;
        System.out.println(AnsiConsole.colorize("PLayerPollAnswerActionDispatcher", AnsiConsole.BrightColor.YELLOW));

        sendResponse(SendMessageData.builder().build());
    }

    private void processText(MessageRequest messageRequest) {

    }

    private void processPayload(MessageRequest messageRequest) {
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.POLL_ANSWER);
    }

    @Override
    public UserRole getRole() {
        return UserRole.PLAYER;
    }

}
