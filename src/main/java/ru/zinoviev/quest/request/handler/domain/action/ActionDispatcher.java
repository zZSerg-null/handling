package ru.zinoviev.quest.request.handler.domain.action;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public abstract class ActionDispatcher {

    private final ResponsePublisher publisher;
    private final PropertiesReader propertiesReader;

    private Map<String, String> buttonsMap;
    private Map<String, String> messageMap;

    public abstract void dispatch(RequestData request);

    public abstract DispatchKey key();

    public void loadMessagesAndButtons(){
       messageMap = propertiesReader.readMessagesForRole(getRole());
       buttonsMap = propertiesReader.readButtonsForRole(getRole());
    };

    public abstract UserRole getRole();

    protected void sendResponse(ResponseData responseData){
        publisher.sendResponse(responseData);
    }

}
