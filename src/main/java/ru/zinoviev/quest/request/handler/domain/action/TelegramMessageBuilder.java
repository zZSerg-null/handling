package ru.zinoviev.quest.request.handler.domain.action;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.DeleteMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.EditMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.dto.internal.QuestInfo;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramMessageBuilder {

    private final KeyboardRegistry keyboardRegistry;
    private final MessageRegistry messageRegistry;

    public ResponseData getDeleteMessageResponse(RequestData requestData) {
        return DeleteMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .build();
    }

    public ResponseData getSendMessageResponse(RequestData requestData, MessageDefinition definition){
        return SendMessageData.builder()
                .userId(requestData.getTelegramId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(definition.getKeyboard(keyboardRegistry))
                .build();
    }

    public ResponseData getEditMessageResponse(RequestData requestData, MessageDefinition definition){
        return EditMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(definition.getKeyboard(keyboardRegistry))
                .build();
    }

    public ResponseData getEditMessageResponse(RequestData requestData, MessageDefinition definition, List<QuestInfo> buttons){
        return EditMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .message(definition.getMessage(messageRegistry))
                .responseKeyboard(keyboardRegistry.buildKeyboard(buttons, definition))
                .build();
    }
}
