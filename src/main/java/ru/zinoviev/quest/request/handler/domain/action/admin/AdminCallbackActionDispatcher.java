package ru.zinoviev.quest.request.handler.domain.action.admin;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.ResponseFactory;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class AdminCallbackActionDispatcher extends ActionDispatcher {

    public AdminCallbackActionDispatcher(ResponseFactory responseFactory, ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(responseFactory, publisher, keyboardRegistry, messageRegistry);
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

