package ru.old.mapper;

import old.domain.dto.request.*;
import old.rest.dto.response.RestMessageEntityData;
import old.rest.dto.response.RestSendFile;
import old.rest.dto.response.keyboard.RestButtonData;
import old.rest.dto.response.keyboard.RestButtonRow;
import old.rest.dto.response.keyboard.RestRespKeyboard;
import org.springframework.stereotype.Component;
import ru.quest_bot.data_handler_service.domain.dto.request.*;
import ru.quest_bot.data_handler_service.old.domain.dto.request.*;
import old.domain.dto.request.message.BasicFile;
import old.domain.dto.request.message.Location;
import old.domain.dto.request.message.RequestOwner;
import old.domain.dto.response.MessageEntityData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.dto.response.SendFile;
import old.domain.dto.response.keyboard.ButtonData;
import old.domain.dto.response.keyboard.ButtonRow;
import old.domain.dto.response.keyboard.RespKeyboard;
import old.domain.enums.MessageEntityType;
import old.domain.enums.RequestMessageType;
import old.rest.dto.response.RestResponseMessageData;
import old.rest.dto.request.RestCallbackQuery;
import old.rest.dto.request.RestMessage;
import old.rest.dto.request.RestPollAnswer;
import old.rest.dto.request.RestUpdateData;
import old.rest.dto.request.message.RestBasicFile;
import old.rest.dto.request.message.RestLocation;
import old.rest.dto.request.message.RestUser;
import old.rest.dto.response.RestResponseData;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestMapper {

    public RequestData toServiceDto(RestUpdateData update) {
        return RequestData.builder()
                .message(getMessage(update.getMessage()))
                .messageType(getMessageType(update))
                .from(getFrom(update.getFrom()))
                .callbackData(getCallbackQuery(update.getCallbackQuery()))
                .pollAnswer(getPollAnswer(update.getPollAnswer()))
                .build();
    }

    private RequestMessageType getMessageType(RestUpdateData update){
        if (update.getMessage() != null) {
            return RequestMessageType.MESSAGE;
        } else if (update.getCallbackQuery() != null) {
            return RequestMessageType.CALLBACK;
        } else if (update.getPollAnswer() != null) {
            return RequestMessageType.POLL_ANSWER;
        }
        return RequestMessageType.UNKNOWN;
    }

    private Message getMessage(RestMessage message) {
        if (message == null) return null;

        return Message.builder()
                .text(message.getText())
                .messageId(message.getMessageId())
                .chatId(message.getChatId())
                .caption(message.getCaption())
                .date(message.getDate())
                .file(getFile(message.getFile()))
                .hasMediaSpoiler(message.getHasMediaSpoiler())
                .location(getLocation(message.getLocation()))
                .mediaGroupId(message.getMediaGroupId())
                .successfulPayment(message.getSuccessfulPayment())
                .build();
    }

    private BasicFile getFile(RestBasicFile basicFile) {
        if (basicFile == null) return null;

        return BasicFile.builder()
                .fileType(FileType.valueOf(basicFile.getFileType().name()))
                .fileId(basicFile.getFileId())
                .fileUniqueId(basicFile.getFileUniqueId())
                .width(basicFile.getWidth())
                .height(basicFile.getHeight())
                .duration(basicFile.getDuration())
                .fileSize(basicFile.getFileSize())
                .fileName(basicFile.getFileName())
                .mimeType(basicFile.getMimeType())
                .type(basicFile.getType())
                .emoji(basicFile.getEmoji())
                .isAnimated(basicFile.getIsAnimated())
                .isVideo(basicFile.getIsVideo())
                .filePath(basicFile.getFilePath())
                .hasMediaSpoiler(basicFile.getHasMediaSpoiler())
                .build();
    }

    private Location getLocation(RestLocation location) {
        if (location == null) return null;

        return Location.builder()
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .livePeriod(location.getLivePeriod())
                .build();
    }


    private RequestOwner getFrom(RestUser user) {
        if (user == null) {
            throw new RuntimeException("Юзер не может быть пуст");
        }

        return RequestOwner.builder()
                .telegramUserId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .languageCode(user.getLanguageCode())
                .build();
    }

    private CallbackData getCallbackQuery(RestCallbackQuery callbackQuery) {
        if (callbackQuery == null) return null;
        return CallbackData.builder()
                .data(callbackQuery.getData())
                .messageId(callbackQuery.getMessageId())
                .build();
    }

    private PollAnswer getPollAnswer(RestPollAnswer pollAnswer) {
        if (pollAnswer == null) return null;

        return PollAnswer.builder()
                .pollId(pollAnswer.getPollId())
                .optionIds(pollAnswer.getOptionIds())
                .build();
    }


////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////


    /**
     * Маппинг ответа сервиса после обработки запроса в транспорт Rest
     *
     * @param questResponseData - ответ сервиса
     * @return - транспорт
     */
    public RestResponseData toRestResponse(QuestResponseData questResponseData) {
        return RestResponseData.builder()
                .messageDataList(mapRestMessages(questResponseData.getMessageDataList()))
                .build();
    }

    private List<RestResponseMessageData> mapRestMessages(List<RespMessageData> messageDataList) {
        return messageDataList.stream()
                .map(this::mapMessage)
                .collect(Collectors.toList());
    }

    private RestResponseMessageData mapMessage(RespMessageData message) {
        if (message == null) return null;
        return RestResponseMessageData.builder()
                .messageType(message.getMessageType())
                .chatId(message.getChatId())
                .protectContent(message.isProtectContent())
                .parseMode(message.getParseMode())
                .messageId(message.getMessageId())
                .text(message.getText())
                .entity(getEntityList(message.getEntity()))
                .replyMarkup(getReplyMarkup(message.getReplyMarkup()))
                .pollType(message.getPollType())
                .question(message.getQuestion())
                .options(message.getOptions())
                .correctOptionId(message.getCorrectOptionId())
                .allowMultipleAnswers(message.getAllowMultipleAnswers())
                .explanation(message.getExplanation())
                .explanationEntities(getEntityList(message.getExplanationEntities()))
                .mediaList(getMediaList(message.getMediaList()))
                .latitude(message.getLatitude())
                .longitude(message.getLongitude())
                .build();
    }

    private List<RestSendFile> getMediaList(List<SendFile> mediaList) {
        if (mediaList == null) return null;
        return mediaList.stream().map(this::getSendFile).collect(Collectors.toList());
    }

    private RestSendFile getSendFile(SendFile sendFile) {
        if (sendFile == null) return null;
        var builder = RestSendFile.builder()
                .caption(sendFile.getCaption())
                .sendFileType(sendFile.getSendFileType())
                .fileUrl(sendFile.getFileUrl())
                .hasSpoiler(sendFile.isHasSpoiler());

        if (sendFile.getCaptionEntities() != null) {
            builder.captionEntities(getEntityList(sendFile.getCaptionEntities()));
        }
        return builder.build();
    }

    private RestRespKeyboard getReplyMarkup(RespKeyboard replyMarkup) {
        if (replyMarkup == null) return null;
        var builder = RestRespKeyboard.builder()
                .keyboardType(replyMarkup.getKeyboardType())
                .removeKeyboard(replyMarkup.isRemoveKeyboard())
                .resizeKeyboard(replyMarkup.isResizeKeyboard());

        if (replyMarkup.getButtonRows() != null) {
            builder.restButtonRows(getRowList(replyMarkup.getButtonRows()));
        }
        return builder.build();
    }

    private List<RestMessageEntityData> getEntityList(List<MessageEntityData> entity) {
        if (entity == null) return null;
        return entity.stream()
                .map(this::getEntity)
                .collect(Collectors.toList());
    }

    private RestMessageEntityData getEntity(MessageEntityData entity) {
        if (entity == null) return null;
        var builder = RestMessageEntityData.builder();

        if (entity.getText() != null) {
            builder.text(entity.getText());
        }
        if (entity.getEntityType() != null) {
            if (entity.getEntityType() == MessageEntityType.TEXT_LINK) {
                if (entity.getUrl() != null) {
                    builder.url(entity.getUrl());
                    builder.entityType(entity.getEntityType());
                }
            } else {
                builder.entityType(entity.getEntityType());
            }
        }
        if (entity.getOffset() != null) {
            builder.offset(entity.getOffset());
        }
        if (entity.getLength() != null) {
            builder.length(entity.getLength());
        }
        return builder.build();
    }


    private List<RestButtonRow> getRowList(List<ButtonRow> buttons) {
        if (buttons == null) return null;
        return buttons.stream()
                .map(this::getRow)
                .collect(Collectors.toList());
    }

    private RestButtonRow getRow(ButtonRow buttons) {
        if (buttons == null) return null;
        return RestButtonRow.builder()
                .buttons(getButtons(buttons.getButtons()))
                .build();
    }

    private List<RestButtonData> getButtons(List<ButtonData> buttons) {
        if (buttons == null) return null;
        return buttons.stream()
                .map(this::getButton)
                .collect(Collectors.toList());
    }

    private RestButtonData getButton(ButtonData buttonData) {
        if (buttonData == null) return null;
        var builder = RestButtonData.builder()
                .text(buttonData.getText());

        if (buttonData.getUrl() != null) {
            builder.url(buttonData.getUrl());
        }
        if (buttonData.getCallbackData() != null) {
            builder.callbackData(buttonData.getCallbackData());
        }
        return builder.build();
    }
}
