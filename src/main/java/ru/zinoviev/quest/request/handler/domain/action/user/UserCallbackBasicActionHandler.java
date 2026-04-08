package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.TelegramMessageBuilder;
import ru.zinoviev.quest.request.handler.domain.dto.internal.CallbackRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.CallbackNames;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.dto.internal.QuestInfo;
import ru.zinoviev.quest.request.handler.domain.db.service.QuestRepositoryService;
import ru.zinoviev.quest.request.handler.domain.db.service.UserRepositoryService;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class UserCallbackBasicActionHandler extends BasicActionHandler {

    private final UserRepositoryService userRepositoryService;
    private final QuestRepositoryService questRepositoryService;

    private final Map<String, Consumer<CallbackRequest>> callbackHandlers = Map.of(
            CallbackNames.QUEST_MENU.getCallbackData(), this::questMenu,
            CallbackNames.QUEST_LIST.getCallbackData(), this::questList,
            CallbackNames.CREATE_NEW_QUEST.getCallbackData(), this::createQuest,
            CallbackNames.START_CREATION.getCallbackData(), this::startQuestCreation,
            CallbackNames.START_QUEST.getCallbackData(), this::startQuest,
            CallbackNames.RUNNING.getCallbackData(), this::runningQuests,
            CallbackNames.ACCOUNT.getCallbackData(), this::account
    );

    public UserCallbackBasicActionHandler(ResponsePublisher publisher,
                                          TelegramMessageBuilder messageBuilder,
                                          UserRepositoryService userRepositoryService,
                                          QuestRepositoryService questRepositoryService) {
        super(publisher, messageBuilder);
        this.userRepositoryService = userRepositoryService;
        this.questRepositoryService = questRepositoryService;
    }

    @Override
    public void dispatch(RequestData request) {
        if (!(request instanceof CallbackRequest callbackRequest)) {
            throw new IllegalStateException(
                    "Expected CallbackRequest but got " + request.getClass()
            );
        }
        System.out.println(
                AnsiConsole.colorize("UserCallbackActionDispatcher", AnsiConsole.BrightColor.YELLOW)
        );


        callbackHandlers
                .getOrDefault(callbackRequest.getCallbackData(), this::ignore)
                .accept(callbackRequest);
    }

    private void runningQuests(CallbackRequest requestData) {
        List<QuestInfo> questInfoList = questRepositoryService.getQuestListByUserId(requestData.getUserId());

        if (questInfoList.isEmpty()) {
            sendEditMessageResponse(requestData, MessageDefinition.USER_RUNNING_QUESTS_EMPTY);
            sendMessageResponse(requestData, MessageDefinition.QUEST_MENU);
        } else {
            sendEditMessageResponse(requestData, MessageDefinition.USER_RUNNING_QUESTS, questInfoList);
        }
    }

    private void createQuest(CallbackRequest requestData) {
        sendEditMessageResponse(requestData, MessageDefinition.CREATE_NEW_QUEST);
    }

    private void startQuestCreation(CallbackRequest requestData) {
        userRepositoryService.setRole(requestData.getUserId(), BotUserRole.CREATOR);
        sendDeleteMessageResponse(requestData);
        sendMessageResponse(requestData, MessageDefinition.START_CREATION);
    }


    private void questList(CallbackRequest requestData) {
        List<QuestInfo> questInfoList = questRepositoryService.getQuestListByUserId(requestData.getUserId());

        if (questInfoList.isEmpty()) {
            sendEditMessageResponse(requestData, MessageDefinition.USER_QUESTS_EMPTY);
            sendMessageResponse(requestData, MessageDefinition.QUEST_MENU);
        } else {
            sendEditMessageResponse(requestData, MessageDefinition.USER_QUESTS, questInfoList);
        }

    }

    private void startQuest(CallbackRequest requestData) {
        List<QuestInfo> questInfoList = questRepositoryService.getQuestListByUserId(requestData.getUserId());

        if (questInfoList.isEmpty()) {
            sendEditMessageResponse(requestData, MessageDefinition.USER_QUESTS_EMPTY);
            sendMessageResponse(requestData, MessageDefinition.QUEST_MENU);
        } else {
            sendEditMessageResponse(requestData, MessageDefinition.USER_RUN_QUEST, questInfoList);
        }
    }

    private void questMenu(CallbackRequest req) {
        sendEditMessageResponse(req, MessageDefinition.QUEST_MENU);
    }

    private void account(CallbackRequest req) {
        System.out.println("ACCOUNT");
    }

    @Override
    public DispatchKey key() {
        return new DispatchKey(getRole(), RequestType.CALLBACK);
    }

    @Override
    public BotUserRole getRole() {
        return BotUserRole.USER;
    }
}

