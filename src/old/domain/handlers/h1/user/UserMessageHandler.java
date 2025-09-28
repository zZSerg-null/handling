package ru.old.domain.handlers.h1.user;

import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.RespMessageType;
import old.domain.enums.RetryType;
import ru.old.domain.handlers.h1.BasicHandler;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;

import java.util.List;

public class UserMessageHandler extends BasicMessageHandler implements BasicHandler {

    public UserMessageHandler(UserMessageBuilder userMessageBuilder) {
        super(userMessageBuilder);
    }

    @Override
    public QuestResponseData handle(RequestData request, UserInfo userInfo) {
        System.out.println(userInfo);

        if (userInfo.getQuestUserId() == -1){
            System.out.println("NEW USER");
            //вернуть приветствие и стандартную клавиатуру

            return QuestResponseData.builder()
                    .messageDataList(List.of(responseMessageBuilder.buildDe(request)))
                    .build();
        }

        System.out.println("HANDLING.....");
        return QuestResponseData.builder()
                .messageDataList(List.of(responseMessageBuilder.buildUnexpectedMessage(request)))
                .build();
    }


    public RespMessageData getUnexpectedBehaviorMessage(RequestData requestData) {
        return RespMessageData.builder()
                .messageType(RespMessageType.MESSAGE)
                .text("requestData.getCallbackData() != null ? UNEXPECTED_CALLBACK_MESSAGE_EXCEPTION : UNEXPECTED_MESSAGE_EXCEPTION")
                .retryType(RetryType.IGNORE)
                .replyMarkup(UserKeyboardsKeeper)
                .chatId(requestData.getFrom().getTelegramUserId())
                .build();
    }
}
