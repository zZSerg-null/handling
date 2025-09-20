package ru.zinoviev.quest.request.handler.jpa.mapper;

import old.domain.dto.response.MessageEntityData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import old.domain.dto.response.SendFile;
import old.domain.dto.response.keyboard.ButtonData;
import old.domain.dto.response.keyboard.ButtonRow;
import old.domain.dto.response.keyboard.RespKeyboard;
import ru.old.domain.enums.RetryType;
import ru.old.domain.enums.UserRole;
import old.domain.jpa.entity.LastResponseJPA;
import old.domain.jpa.entity.last_response.MessageEntityDataJPA;
import old.domain.jpa.entity.last_response.keyboard.ButtonDataJPA;
import old.domain.jpa.entity.last_response.keyboard.ButtonRowJPA;
import old.domain.jpa.entity.last_response.keyboard.RespKeyboardJPA;
import org.springframework.stereotype.Component;
import old.domain.jpa.entity.last_response.MessageDataJPA;
import old.domain.jpa.entity.last_response.SendFileJPA;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LastResponseJPAMapper {

    public LastResponseJPA toEntity(QuestResponseData responseData, UserRole status) {
        return LastResponseJPA.builder()
                .telegramId(responseData.getMessageDataList().get(0).getChatId())
                .userRole(status)
                .messageDataList(buildMessageList(responseData.getMessageDataList()))
                .build();
    }

    private List<MessageDataJPA> buildMessageList(List<RespMessageData> messageDataList) {
        return messageDataList.stream()
                .filter(message -> message.getRetryType().equals(RetryType.RETRYABLE))
                .map(rmd -> {
                    return MessageDataJPA.builder()
                            .messageType(rmd.getMessageType())
                            .chatId(rmd.getChatId())
                            .text(rmd.getText())
                            .parseMode(rmd.getParseMode())
                            .messageId(rmd.getMessageId()) // ID сообщения, которое будет изменено \ удалено
                            .protectContent(rmd.isProtectContent()) // запрещена ли пересылка сообщений в другие чаты
                            .pollType(rmd.getPollType())
                            .question(rmd.getQuestion())
                            .correctOptionId(rmd.getCorrectOptionId())
                            .allowMultipleAnswers(rmd.getAllowMultipleAnswers()) // если true то explanation и explanationEntities не работает
                            .explanation(rmd.getExplanation())
                            .latitude(rmd.getLatitude())
                            .longitude(rmd.getLongitude())
                            .options(rmd.getOptions())

                            .explanationEntities(entitiesToJPA(rmd.getExplanationEntities()))
                            .mediaList(medialistToJPA(rmd.getMediaList()))
                            .entity(entitiesToJPA(rmd.getEntity()))
                            .replyMarkup(markupToJPA(rmd.getReplyMarkup()))
                            .build();
                })
                .collect(Collectors.toList());
    }

    private List<MessageEntityDataJPA> entitiesToJPA(List<MessageEntityData> explanationEntities) {
        if (explanationEntities == null) return null;
        return explanationEntities.stream()
                .map(med -> MessageEntityDataJPA.builder()
                        .url(med.getUrl())
                        .text(med.getText())
                        .entityType(med.getEntityType())
                        .length_x(med.getLength())
                        .offset_x(med.getOffset())
                        .build()
                )
                .collect(Collectors.toList());
    }

    private SendFileJPA sendfileToJPA(SendFile sendFile) {
        if (sendFile == null) return null;
        return SendFileJPA.builder()
                .caption(sendFile.getCaption())
                .captionEntities(entitiesToJPA(sendFile.getCaptionEntities()))
                .hasSpoiler(sendFile.isHasSpoiler())
                .fileUrl(sendFile.getFileUrl())
                .sendFileType(sendFile.getSendFileType())
                .build();
    }

    private List<SendFileJPA> medialistToJPA(List<SendFile> mediaList) {
        if (mediaList == null) return null;
        return mediaList.stream()
                .map(this::sendfileToJPA)
                .collect(Collectors.toList());
    }

    private RespKeyboardJPA markupToJPA(RespKeyboard replyMarkup) {
        if (replyMarkup == null) return null;
        return RespKeyboardJPA.builder()
                .buttonRowJPAS(buttonsToJPA(replyMarkup.getButtonRows()))
                .keyboardType(replyMarkup.getKeyboardType())
                .removeKeyboard(replyMarkup.isRemoveKeyboard())
                .resizeKeyboard(replyMarkup.isResizeKeyboard())
                .build();
    }

    private List<ButtonRowJPA> buttonsToJPA(List<ButtonRow> buttonRows) {
        List<ButtonRowJPA> buttonRowJPAS = new ArrayList<>();

        buttonRows
                .forEach(buttonRow -> {
                    var buttonRowJpa = ButtonRowJPA.builder()
                            .buttons(
                                    buttonRow.getButtons().stream()
                                            .map(b -> ButtonDataJPA.builder()
                                                    .callbackData(b.getCallbackData())
                                                    .text(b.getText())
                                                    .url(b.getUrl())
                                                    .build())
                                            .collect(Collectors.toList())
                            ).build();
                    buttonRowJPAS.add(buttonRowJpa);
                });
        return buttonRowJPAS;
    }

    public QuestResponseData toResponse(LastResponseJPA entity) {
        return QuestResponseData.builder()
                .messageDataList(messagesToResponse(entity.getMessageDataList()))
                .build();
    }

    public List<RespMessageData> toMessageList(List<MessageDataJPA> messageDataList) {
        return messagesToResponse(messageDataList);
    }

    private List<RespMessageData> messagesToResponse(List<MessageDataJPA> messageDataList) {
        return messageDataList.stream()
                .map(this::getResponseMessage)
                .collect(Collectors.toList());
    }

    private RespMessageData getResponseMessage(MessageDataJPA dataJPA) {
        return RespMessageData.builder()
                .messageType(dataJPA.getMessageType())
                .chatId(dataJPA.getChatId())
                .retryType(RetryType.IGNORE)
                .parseMode(dataJPA.getParseMode())
                .text(dataJPA.getText())
                .messageId(dataJPA.getMessageId()) // ID сообщения, которое будет изменено \ удалено
                .protectContent(dataJPA.isProtectContent()) // запрещена ли пересылка сообщений в другие чаты
                .pollType(dataJPA.getPollType())
                .question(dataJPA.getQuestion())
                .correctOptionId(dataJPA.getCorrectOptionId())
                .allowMultipleAnswers(dataJPA.getAllowMultipleAnswers()) // если true то explanation и explanationEntities не работает
                .explanation(dataJPA.getExplanation())
                .latitude(dataJPA.getLatitude())
                .longitude(dataJPA.getLongitude())
                .options(dataJPA.getOptions())

                .explanationEntities(entitiesToDTO(dataJPA.getExplanationEntities()))
                .mediaList(medialistToDTO(dataJPA.getMediaList()))
                .entity(entitiesToDTO(dataJPA.getEntity()))
                .replyMarkup(markupToDTO(dataJPA.getReplyMarkup()))
                .build();
    }

    private RespKeyboard markupToDTO(RespKeyboardJPA replyMarkup) {
        if (replyMarkup == null) return null;

        return RespKeyboard.builder()
                .buttonRows(JPAbuttonsToDTO(replyMarkup.getButtonRowJPAS()))
                .keyboardType(replyMarkup.getKeyboardType())
                .removeKeyboard(replyMarkup.isRemoveKeyboard())
                .resizeKeyboard(replyMarkup.isResizeKeyboard())
                .build();
    }

    private SendFile sendfileToDTO(SendFileJPA sendFileJPA) {
        if (sendFileJPA == null) return null;
        return SendFile.builder()
                .caption(sendFileJPA.getCaption())
                .captionEntities(entitiesToDTO(sendFileJPA.getCaptionEntities()))
                .hasSpoiler(sendFileJPA.getHasSpoiler())
                .fileUrl(sendFileJPA.getFileUrl())
                .sendFileType(sendFileJPA.getSendFileType())
                .build();
    }


    private List<SendFile> medialistToDTO(List<SendFileJPA> mediaList) {
        if (mediaList == null) return null;
        return mediaList.stream()
                .map(this::sendfileToDTO)
                .collect(Collectors.toList());
    }

    private List<MessageEntityData> entitiesToDTO(List<MessageEntityDataJPA> entity) {
        if (entity == null) return null;
        return entity.stream()
                .map(med -> MessageEntityData.builder()
                        .url(med.getUrl())
                        .text(med.getText())
                        .entityType(med.getEntityType())
                        .length(med.getLength_x())
                        .offset(med.getOffset_x())
                        .build()
                )
                .collect(Collectors.toList());
    }


    private List<ButtonRow> JPAbuttonsToDTO(List<ButtonRowJPA> buttonRows) {
        List<ButtonRow> buttonRows1 = new ArrayList<>();

        buttonRows
                .forEach(rowJPA -> {
                    var buttonRowDTO = ButtonRow.builder()
                            .buttons(
                                    rowJPA.getButtons().stream()
                                            .map(b -> ButtonData.builder()
                                                    .callbackData(b.getCallbackData())
                                                    .text(b.getText())
                                                    .url(b.getUrl())
                                                    .build())
                                            .collect(Collectors.toList())
                            ).build();
                    buttonRows1.add(buttonRowDTO);
                });
        return buttonRows1;
    }

}
