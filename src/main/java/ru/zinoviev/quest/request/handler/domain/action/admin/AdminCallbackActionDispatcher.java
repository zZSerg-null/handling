package ru.zinoviev.quest.request.handler.domain.action.admin;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.PropertiesReader;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.Map;

@Component
public class AdminCallbackActionDispatcher extends ActionDispatcher {

    public AdminCallbackActionDispatcher(ResponsePublisher publisher, PropertiesReader propertiesReader) {
        super(publisher, propertiesReader);
    }

    @Override
    public void dispatch(RequestData request) {
        System.out.println("AdminCallbackActionDispatcher");
        sendResponse(SendMessageData.builder().build());
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.CALLBACK);
    }

    @Override
    public UserRole getRole() {
        return UserRole.ADMIN;
    }

    private void play(RequestData req) {
        // логика "play"
    }

    private void pause(RequestData req) {
        // логика "pause"
    }

    private void unknown(RequestData req) {
        // fallback для неизвестного payload
    }

    // Локальный handler-интерфейс
    private interface Handler {
        void handle(RequestData req);
    }


}

