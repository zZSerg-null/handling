package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.CallbackRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class CreatorCallbackBasicActionHandler extends BasicActionHandler {

    private final Map<String, Consumer<CallbackRequest>> callbackHandlers = Map.of(
//            CallbackNames.QUEST_MENU.getCallbackData(), this::questMenu,
//            CallbackNames.QUEST_LIST.getCallbackData(), this::questList,
//            CallbackNames.CREATE_NEW_QUEST.getCallbackData(), this::createQuest,
//            CallbackNames.START_CREATION.getCallbackData(), this::startQuestCreation,
//            CallbackNames.START_QUEST.getCallbackData(), this::startQuest,
//            CallbackNames.RUNNING.getCallbackData(), this::runningQuests,
//            CallbackNames.ACCOUNT.getCallbackData(), this::account,
    );


    public CreatorCallbackBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder) {
        super(publisher, messageBuilder);
    }

    @Override
    public void dispatch(RequestData request) {
        if (!(request instanceof CallbackRequest callbackRequest)) {
            throw new IllegalStateException(
                    "Expected CallbackRequest but got " + request.getClass()
            );
        }

        System.out.println(AnsiConsole.colorize("CreatorCallbackActionDispatcher", AnsiConsole.BrightColor.YELLOW));

        callbackHandlers
                .getOrDefault(callbackRequest.getCallbackData(), this::ignore)
                .accept(callbackRequest);


        sendMessageResponse(callbackRequest, MessageDefinition.TEST_REPLY);
    }


    private void stopCreating(MessageRequest messageRequest) {
        sendEditMessageResponse(messageRequest, MessageDefinition.REMOVE_REPLY);
        sendMessageResponse(messageRequest, MessageDefinition.REMOVE_REPLY);
        sendMessageResponse(messageRequest, MessageDefinition.QUEST_MENU);
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.CALLBACK);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.CREATOR;
    }


}

