package ru.zinoviev.quest.request.handler.domain.dto.request;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.transport.request.dto.*;

import java.util.Map;
import java.util.function.Function;

@Component
public class RequestDataMapper {

    private final Map<PayloadType, Function<FilePayload, PayloadObject>> payloadTypeMap = Map.of(
            PayloadType.AUDIO, this::getAudio,
            PayloadType.ANIMATION, this::getAnimation,
            PayloadType.PHOTO, this::getPhoto,
            PayloadType.VOICE, this::getVoice,
            PayloadType.VIDEO, this::getVideo,
            PayloadType.DOCUMENT, this::getDocument,
            PayloadType.STICKER, this::getSticker
    );

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
                        payloadTypeMap
                                .get(request.getFilePayload().getPayloadType() )
                                .apply(request.getFilePayload())

                        : null
                )
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



    private PayloadObject getAudio(FilePayload payload){
        return PayloadObject.builder()
                .payloadType(PayloadType.AUDIO)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .duration(payload.getDuration())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getAnimation(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.ANIMATION)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .height(payload.getHeight())
                .width(payload.getWidth())
                .duration(payload.getDuration())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getPhoto(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.PHOTO)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .filePath(payload.getFilePath())
                .height(payload.getHeight())
                .width(payload.getWidth())
                .fileSize(payload.getFileSize())
                .build();
    }

    private PayloadObject getVoice(FilePayload filePayload) {
        return PayloadObject.builder().build();
    }

    private PayloadObject getVideo(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.VIDEO)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .width(payload.getWidth())
                .height(payload.getHeight())
                .duration(payload.getDuration())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getDocument(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.DOCUMENT)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .fileName(payload.getFileName())
                .fileSize(payload.getFileSize())
                .mimeType(payload.getMimeType())
                .build();
    }

    private PayloadObject getSticker(FilePayload payload) {
        return PayloadObject.builder()
                .payloadType(PayloadType.STICKER)
                .caption(payload.getCaption())
                .fileId(payload.getFileId())
                .fileUniqueId(payload.getFileUniqueId())
                .width(payload.getWidth())
                .height(payload.getHeight())
                .isAnimated(payload.getIsAnimated())
                .build();
    }



}
