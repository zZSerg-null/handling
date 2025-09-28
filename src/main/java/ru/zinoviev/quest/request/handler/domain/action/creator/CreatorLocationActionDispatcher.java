package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.PropertiesReader;
import ru.zinoviev.quest.request.handler.domain.dto.request.LocationRequest;
import ru.zinoviev.quest.request.handler.domain.dto.request.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorLocationActionDispatcher extends ActionDispatcher {

    public CreatorLocationActionDispatcher(ResponsePublisher publisher, PropertiesReader propertiesReader) {
        super(publisher, propertiesReader);
    }

    public void dispatch(RequestData request) {
        LocationRequest locationRequest = (LocationRequest) request;
        System.out.println("CreatorLocationActionDispatcher");

        sendResponse(SendMessageData.builder().build());
    }

    private void processText(MessageRequest messageRequest) {

    }

    ;

    private void processPayload(MessageRequest messageRequest) {
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.LOCATION);
    }

    @Override
    public UserRole getRole() {
        return UserRole.CREATOR;
    }

}
