package ru.zinoviev.quest.request.handler.domain.dto.response.utils;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.*;

@Component
public class ResponseFactory {

    /**
     * @param userId - id пользователя телеги
     * @param message - текст отправляемого сообщения
     * @param responseKeyboard - клавиатура, если есть
     * @param payload - вложение, если есть
     * @return - готовое сообщение
     */
    public SendMessageData getSendMessageResponse(Long userId, String message, ResponseKeyboard responseKeyboard, PayloadResponse payload) {
        return SendMessageData.builder()
                .userId(userId)
                .message(escapeMarkdownV2(message))
                .responseKeyboard(responseKeyboard)
                .payloadResponse(getPayload(payload))
                .build();
    }

    /**
     * @param requestData - входящее сообщение
     * @param message - текст отправляемого сообщения
     * @param responseKeyboard - клавиатура, если есть
     * @return - готовое сообщение
     */
    public EditMessageData getEditMessageResponse(RequestData requestData, String message, ResponseKeyboard responseKeyboard) {
        return EditMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .message(escapeMarkdownV2(message))
                .responseKeyboard(responseKeyboard)
                .build();
    }

    /**
     * @param requestData - входящее сообщение
     * @return - готовое сообщение
     */
    public DeleteMessageData getDeleteMessageResponse(RequestData requestData) {
        return DeleteMessageData.builder()
                .userId(requestData.getTelegramId())
                .messageId(requestData.getMessageId())
                .build();
    }

    private PayloadResponse getPayload(PayloadResponse payload) {
        if (payload == null) {
            return null;
        }

        return PayloadResponse.builder()
                .build();
    }


    private String escapeMarkdownV2(String text) {
        if (text == null) return "";
        return text.replaceAll("([-+\\\\=()#!{}.])", "\\\\$1");
    }

}
