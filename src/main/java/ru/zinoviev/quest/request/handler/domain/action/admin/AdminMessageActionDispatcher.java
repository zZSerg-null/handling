package ru.zinoviev.quest.request.handler.domain.action.admin;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.PropertiesReader;
import ru.zinoviev.quest.request.handler.domain.action.user.UserTextCommand;
import ru.zinoviev.quest.request.handler.domain.dto.request.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class AdminMessageActionDispatcher extends ActionDispatcher {

    public AdminMessageActionDispatcher(ResponsePublisher publisher, PropertiesReader propertiesReader) {
        super(publisher, propertiesReader);
    }

    public void dispatch(RequestData request) {
        MessageRequest messageRequest = (MessageRequest) request;
        System.out.println("AdminMessageActionDispatcher");

        if (messageRequest.getText().toLowerCase().startsWith(AdminTextCommand.AI_CHAT)) {
            routeMessageToAI(messageRequest);
        } else {
            //anyMessage(messageRequest);
        }
//
//        if (messageRequest.getPayloadObject() != null) {
//            processPayload(messageRequest);
//        } else
//            processText(messageRequest);
    }

    private void routeMessageToAI(MessageRequest messageRequest) {
        sendResponse(SendMessageData.builder()
                .build());
    }

    private void processText(MessageRequest messageRequest) {

    }

    private void processPayload(MessageRequest messageRequest) {
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }
}
