package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.PropertiesReader;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class UserCallbackActionDispatcher extends ActionDispatcher {

    private final Map<String, Consumer<RequestData>> callbackHandlers = Map.of(
            "my_quests", this::questMenu,
            "my_account", this::account,
            "create_quest", this::createQuest,
            "start_quest", this::startQuest
    );

    public UserCallbackActionDispatcher(ResponsePublisher publisher, PropertiesReader propertiesReader) {
        super(publisher, propertiesReader);
    }

    @Override
    public void dispatch(RequestData request) {
        System.out.println("UserCallbackActionDispatcher");

        sendResponse(SendMessageData.builder().build());
    }


    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.CALLBACK);
    }

    @Override
    public UserRole getRole() {
        return UserRole.USER;
    }

    private void createQuest(RequestData req) {
        // логика "createQuest"
    }

    private void startQuest(RequestData req) {
        // логика "startQuest"
    }

    private void questMenu(RequestData req) {
        // логика "questMen"
    }

    private void account(RequestData req) {
        // логика "account"
    }

    private void unknown(RequestData req) {
        // логика "account"
    }
}

