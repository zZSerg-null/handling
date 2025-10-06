package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.internal.WebAppRequest;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.ResponseFactory;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorWebAppActionDispatcher extends ActionDispatcher {

    public CreatorWebAppActionDispatcher(ResponseFactory responseFactory, ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(responseFactory, publisher, keyboardRegistry, messageRegistry);
    }

    public void dispatch(RequestData request) {
        WebAppRequest messageRequest = (WebAppRequest) request;
        System.out.println("CreatorWebAppActionDispatcher");

        sendResponse(SendMessageData.builder().build());
    }

    private void processText(MessageRequest messageRequest) {

    }

    private void processPayload(MessageRequest messageRequest) {
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.WEBAPP);
    }

    @Override
    public UserRole getRole() {
        return UserRole.CREATOR;
    }

}
