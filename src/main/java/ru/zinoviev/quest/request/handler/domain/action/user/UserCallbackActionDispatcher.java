package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.CallbackRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.CallbackNames;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.ResponseFactory;
import ru.zinoviev.quest.request.handler.domain.enums.MenuDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class UserCallbackActionDispatcher extends ActionDispatcher {

//    @Value("${bot.mini_app_url}")
//    private String miniAppUrl;

    private final Map<String, Consumer<RequestData>> callbackHandlers = Map.of(
            CallbackNames.QUEST_MENU.getCallbackData(), this::questMenu,
            CallbackNames.QUEST_LIST.getCallbackData(), this::questList,
            CallbackNames.CREATE_QUEST.getCallbackData(), this::createQuest,
            CallbackNames.START_QUEST.getCallbackData(), this::startQuest,
            CallbackNames.RUNNING.getCallbackData(), this::runningQuests,
            CallbackNames.ACCOUNT.getCallbackData(), this::account,
            CallbackNames.BACK.getCallbackData(), this::back
    );

    public UserCallbackActionDispatcher(ResponseFactory responseFactory, ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(responseFactory, publisher, keyboardRegistry, messageRegistry);
    }

    @Override
    public void dispatch(RequestData request) {
        System.out.println("UserCallbackActionDispatcher");

        CallbackRequest callbackRequest = (CallbackRequest) request;
        callbackHandlers
                .getOrDefault(callbackRequest.getCallbackData(), this::ignore)
                .accept(request);
    }

    private void runningQuests(RequestData requestData) {
        System.out.println("RUNNING");
    }

    private void createQuest(RequestData requestData) {
        sendResponse(
                getDefaultSendMessageResponse(requestData, MenuDefinition.CREATE_NEW_QUEST_MENU)
        );
    }

    private void questList(RequestData requestData) {
        System.out.println("QUEST LIST");
    }

    private void startQuest(RequestData req) {
        System.out.println("START QUEST");
    }

    private void questMenu(RequestData req) {
        sendResponse(
                getDefaultEditMessageResponse(req, MenuDefinition.QUEST_MENU)
        );
    }

    private void account(RequestData req) {
        System.out.println("ACCOUNT");
    }


    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.CALLBACK);
    }

    @Override
    public UserRole getRole() {
        return UserRole.USER;
    }
}

