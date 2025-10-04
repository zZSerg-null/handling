package ru.zinoviev.quest.request.handler.domain.dto.request;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.transport.request.dto.*;

@Component
public class RequestDataMapper {

    public RequestData toRequestData(TelegramRequest request) {
        if (request instanceof TelegramCallback) {
            return getCallbackTypeRequest((TelegramCallback) request);
        } else if (request instanceof TelegramMessage) {
            return getMessageTypeRequest((TelegramMessage) request);
        } else if (request instanceof TelegramLocation) {
            return getLocationTypeRequest((TelegramLocation) request);
        } else if (request instanceof TelegramPoll) {
            return getPollTypeRequest((TelegramPoll) request);
        } else if (request instanceof TelegramWebApp){
            return getWebAppRequest((TelegramWebApp) request);
        }
        return getPollAnswerTypeRequest((TelegramPollAnswer) request);
    }

    private RequestData getWebAppRequest(TelegramWebApp request) {
        return WebAppRequest.builder()
                .telegramId(request.getUserId())
                .userName(request.getUserName())
                .messageId(request.getMessageId())
                .webAppData(request.getWebAppData())
                .build();
    }

    private RequestData getCallbackTypeRequest(TelegramCallback request) {
        return CallbackRequest.builder()
                .telegramId(request.getUserId())
                .messageId(request.getMessageId())
                .userName(request.getUserName())
                .callbackData(request.getCallbackData())
                .build();
    }

    private RequestData getMessageTypeRequest(TelegramMessage request) {
        return MessageRequest.builder()
                .telegramId(request.getUserId())
                .userName(request.getUserName())
                .messageId(request.getMessageId())
                .text(request.getText())
                .payloadObject(request.getFilePayload() != null ?
                        getPayloadObjectByType(request.getFilePayload())
                        : null
                )
                .build();
    }

    private PayloadObject getPayloadObjectByType(FilePayload payload){
        return PayloadObject.builder()
                .payloadType(payload.getPayloadType())
                .fileId(payload.getFileId())
                .build();
    }

    private RequestData getLocationTypeRequest(TelegramLocation request) {
        return LocationRequest.builder()
                .telegramId(request.getUserId())
                .messageId(request.getMessageId())
                .userName(request.getUserName())
                .build();
    }

    private RequestData getPollTypeRequest(TelegramPoll request) {
        return PollRequest.builder()
                .telegramId(request.getUserId())
                .messageId(request.getMessageId())
                .userName(request.getUserName())
                .pollId(request.getPollId())
                .type(request.getType())
                .allowMultipleAnswers(request.getAllowMultipleAnswers())
                .question(request.getQuestion())
                .explanation(request.getExplanation())
                .answers(request.getAnswers())
                .correctAnswersId(request.getCorrectAnswersId())
                .build();
    }

    private RequestData getPollAnswerTypeRequest(TelegramPollAnswer request) {
        return PollAnswerRequest.builder()
                .telegramId(request.getUserId())
                .messageId(request.getMessageId())
                .userName(request.getUserName())
                .pollId(request.getPollId())
                .optionIds(request.getOptionIds())
                .build();
    }

}
