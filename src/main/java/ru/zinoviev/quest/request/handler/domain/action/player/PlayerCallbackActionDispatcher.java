package ru.zinoviev.quest.request.handler.domain.action.player;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.CallbackRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.enums.CallbackNames;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class PlayerCallbackActionDispatcher extends ActionDispatcher {

    private final Map<String, Consumer<CallbackRequest>> callbackHandlers = Map.of(
//            CallbackNames.QUEST_MENU.getCallbackData(), this::questMenu,
//            CallbackNames.QUEST_LIST.getCallbackData(), this::questList,
//            CallbackNames.CREATE_NEW_QUEST.getCallbackData(), this::createQuest,
//            CallbackNames.START_CREATION.getCallbackData(), this::startQuestCreation,
//            CallbackNames.START_QUEST.getCallbackData(), this::startQuest,
//            CallbackNames.RUNNING.getCallbackData(), this::runningQuests,
//            CallbackNames.ACCOUNT.getCallbackData(), this::account,
            CallbackNames.BACK.getCallbackData(), this::back
    );

    public PlayerCallbackActionDispatcher(ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(publisher, keyboardRegistry, messageRegistry);
    }

    @Override
    public void dispatch(RequestData request) {
        CallbackRequest callbackRequest = (CallbackRequest) request;
        System.out.println(AnsiConsole.colorize("PlayerCallbackActionDispatcher", AnsiConsole.BrightColor.YELLOW));

        callbackHandlers
                .getOrDefault(callbackRequest.getCallbackData(), this::ignore)
                .accept(callbackRequest);

        sendResponse(SendMessageData.builder().build());
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.CALLBACK);
    }

    @Override
    public UserRole getRole() {
        return UserRole.PLAYER;
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

