package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.domain.jpa.service.QuestRepositoryService;
import ru.zinoviev.quest.request.handler.domain.jpa.service.UserRepositoryService;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorMessageActionDispatcher extends ActionDispatcher {


    private final UserRepositoryService userRepositoryService;
    private final QuestRepositoryService questRepositoryService;


    public CreatorMessageActionDispatcher(ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry, UserRepositoryService userRepositoryService, QuestRepositoryService questRepositoryService) {
        super(publisher, keyboardRegistry, messageRegistry);

        this.userRepositoryService = userRepositoryService;
        this.questRepositoryService = questRepositoryService;
    }

    public void dispatch(RequestData request) {
        MessageRequest messageRequest = (MessageRequest) request;
        System.out.println(AnsiConsole.colorize("CreatorMessageActionDispatcher", AnsiConsole.BrightColor.YELLOW));

        if (messageRequest.getText() != null) {
            processText(messageRequest);
        } else {
            processPayload(messageRequest);
        }
    }

    private void processText(MessageRequest messageRequest) {
        if (messageRequest.getText().equals(CreatorTextCommand.STOP_QUEST_CREATING)) {
            stopCreatingQuestConfirm(messageRequest);
        } else {
            sendResponse(SendMessageData.builder().build());
        }
    }

    private void stopCreatingQuestConfirm(MessageRequest messageRequest) {
        userRepositoryService.setRole(messageRequest.getUserId(), UserRole.USER);

        sendResponse(getSendMessageResponse(messageRequest, MessageDefinition.REMOVE_REPLY));
        sendResponse(getSendMessageResponse(messageRequest, MessageDefinition.QUEST_MENU));
    }


    private void processPayload(MessageRequest messageRequest) {
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public UserRole getRole() {
        return UserRole.CREATOR;
    }

}
