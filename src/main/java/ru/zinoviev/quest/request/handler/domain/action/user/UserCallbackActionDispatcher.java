package ru.zinoviev.quest.request.handler.domain.action.user;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.CallbackRequest;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.enums.CallbackNames;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.domain.jpa.QuestInfo;
import ru.zinoviev.quest.request.handler.domain.jpa.service.QuestRepositoryService;
import ru.zinoviev.quest.request.handler.domain.jpa.service.UserRepositoryService;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component
public class UserCallbackActionDispatcher extends ActionDispatcher {

    private final UserRepositoryService userRepositoryService;
    private final QuestRepositoryService questRepositoryService;

    private final Map<String, Consumer<CallbackRequest>> callbackHandlers = Map.of(
            CallbackNames.QUEST_MENU.getCallbackData(), this::questMenu,
            CallbackNames.QUEST_LIST.getCallbackData(), this::questList,
            CallbackNames.CREATE_NEW_QUEST.getCallbackData(), this::createQuest,
            CallbackNames.START_CREATION.getCallbackData(), this::startQuestCreation,
            CallbackNames.START_QUEST.getCallbackData(), this::startQuest,
            CallbackNames.RUNNING.getCallbackData(), this::runningQuests,
            CallbackNames.ACCOUNT.getCallbackData(), this::account,
            CallbackNames.BACK.getCallbackData(), this::back
    );

    public UserCallbackActionDispatcher(ResponsePublisher publisher,
                                        KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry,
                                        UserRepositoryService userRepositoryService, QuestRepositoryService questRepositoryService) {
        super(publisher, keyboardRegistry, messageRegistry);
        this.userRepositoryService = userRepositoryService;
        this.questRepositoryService = questRepositoryService;
    }


    @Override
    public void dispatch(RequestData request) {
        CallbackRequest callbackRequest = (CallbackRequest) request;
        System.out.println(AnsiConsole.colorize("UserCallbackActionDispatcher", AnsiConsole.BrightColor.YELLOW));


        callbackHandlers
                .getOrDefault(callbackRequest.getCallbackData(), this::ignore)
                .accept(callbackRequest);
    }

    private void runningQuests(CallbackRequest requestData) {
        List<QuestInfo> questInfoList = questRepositoryService.getQuestListByUserId(requestData.getUserId());

        if (questInfoList.isEmpty()) {
            sendResponse(getEditMessageResponse(requestData, MessageDefinition.USER_RUNNING_QUESTS_EMPTY));
            sendResponse(getSendMessageResponse(requestData, MessageDefinition.QUEST_MENU));
        } else {
            sendResponse(
                    getEditMessageResponse(requestData, MessageDefinition.USER_RUNNING_QUESTS, questInfoList)
            );
        }
    }

    private void createQuest(CallbackRequest requestData) {
        sendResponse(
                getEditMessageResponse(requestData, MessageDefinition.CREATE_NEW_QUEST)
        );
    }

    private void startQuestCreation(CallbackRequest requestData) {
        userRepositoryService.setRole(requestData.getUserId(), UserRole.CREATOR);
        sendResponse(
                getDeleteMessageResponse(requestData)
        );
        sendResponse(
                getSendMessageResponse(requestData, MessageDefinition.START_CREATION)
        );
    }


    private void questList(CallbackRequest requestData) {
        List<QuestInfo> questInfoList = questRepositoryService.getQuestListByUserId(requestData.getUserId());

        if (questInfoList.isEmpty()) {
            sendResponse(getEditMessageResponse(requestData, MessageDefinition.USER_QUESTS_EMPTY));
            sendResponse(getSendMessageResponse(requestData, MessageDefinition.QUEST_MENU));
        } else {
            sendResponse(
                    getEditMessageResponse(requestData, MessageDefinition.USER_QUESTS, questInfoList)
            );
        }

    }

    private void startQuest(CallbackRequest requestData) {
        List<QuestInfo> questInfoList = questRepositoryService.getQuestListByUserId(requestData.getUserId());

        if (questInfoList.isEmpty()) {
            sendResponse(getEditMessageResponse(requestData, MessageDefinition.USER_QUESTS_EMPTY));
            sendResponse(getSendMessageResponse(requestData, MessageDefinition.QUEST_MENU));
        } else {
            sendResponse(getEditMessageResponse(requestData, MessageDefinition.USER_RUN_QUEST, questInfoList));
        }
    }

    private void questMenu(CallbackRequest req) {
        sendResponse(
                getEditMessageResponse(req, MessageDefinition.QUEST_MENU)
        );
    }

    private void account(CallbackRequest req) {
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

