package ru.zinoviev.quest.request.handler.domain.action.creator;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.MessageRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.db.service.QuestRepositoryService;
import ru.zinoviev.quest.request.handler.domain.db.service.UserRepositoryService;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class CreatorMessageBasicActionHandler extends BasicActionHandler {


    private final UserRepositoryService userRepositoryService;
    private final QuestRepositoryService questRepositoryService;


    public CreatorMessageBasicActionHandler(ResponsePublisher publisher, TelegramMessageBuilder messageBuilder, UserRepositoryService userRepositoryService, QuestRepositoryService questRepositoryService) {
        super(publisher, messageBuilder);
        this.userRepositoryService = userRepositoryService;
        this.questRepositoryService = questRepositoryService;
    }

    public void dispatch(RequestData request) {
        if (! (request instanceof MessageRequest messageRequest)){
            throw new ClassCastException("Expected message class but got "+ request.getClass());
        }


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
            sendMessageResponse(messageRequest, MessageDefinition.TEST_REPLY);
        }
    }

    private void stopCreatingQuestConfirm(MessageRequest messageRequest) {
        userRepositoryService.setRole(messageRequest.getUserId(), BotUserRole.USER);

        sendMessageResponse(messageRequest, MessageDefinition.REMOVE_REPLY);
        sendMessageResponse(messageRequest, MessageDefinition.QUEST_MENU);
    }


    private void processPayload(MessageRequest messageRequest) {
        //TODO
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.MESSAGE);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.CREATOR;
    }

}
