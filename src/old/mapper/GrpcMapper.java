package ru.old.mapper;

import org.springframework.stereotype.Component;

@Component
public class GrpcMapper {
//
//    public RequestData toServiceDto(GrpcUpdateData grpc) {
//        return RequestData.builder()
//                .message(getMessage(grpc))
//                .from(getFrom(grpc))
//                .callback(
//                        grpc.hasCallbackQuery() ?
//                                CallbackData.builder()
//                                        .callbackData(grpc.getCallbackQuery().getData())
//                                        .messageId(grpc.getCallbackQuery().getMessageId()
//                                        ).build()
//                                : null)
//                .pollAnswer(getPollAnswer(grpc))
//                .build();
//    }
//
//    private Message getMessage(GrpcUpdateData update) {
//        if (!update.hasMessage()) return null;
//
//        GrpcMessage message = update.getMessage();
//        return Message.builder()
//                .messageId(message.getMessageId())
//                .text(message.getText())
//                .caption(message.getCaption())
//                .chatId(message.getChatId())
//                .date(message.getDate())
//                .hasMediaSpoiler(message.getHasMediaSpoiler())
//                .successfulPayment(message.getSuccessfulPayment())
//                .mediaGroupId(message.getMediaGroupId())
//                .isEditedMessage(message.getEditedMessage())
//                .location(getLocation(message))
//                .photo(getPhoto(message))
//                .basicFile(getFile(message))
//                .build();
//    }
//
//    private Location getLocation(GrpcMessage message) {
//        if (!message.hasLocation()) return null;
//
//        GrpcLocation location = message.getLocation();
//        return Location.builder()
//                .latitude(location.getLatitude())
//                .longitude(location.getLongitude())
//                .livePeriod(location.getLivePeriod())
//                .build();
//    }
//
//    private List<PhotoInfo> getPhoto(GrpcMessage message) {
//        if (message.getPhotoList().isEmpty()) return null;
//
//        List<GrpcPhotoSize> photos = message.getPhotoList();
//        return photos.stream().map(this::getPhoto).toList();
//    }
//
//    private PhotoInfo getPhoto(GrpcPhotoSize photo) {
//        if (photo == null) return null;
//        return PhotoInfo.builder()
//                .fileId(photo.getFileId())
//                .fileUniqueId(photo.getFileUniqueId())
//                .width(photo.getWidth())
//                .height(photo.getHeight())
//                .filePath(photo.getFilePath())
//                .fileSize(photo.getFileSize())
//                .build();
//    }
//
//    private BasicFile getFile(GrpcMessage message) {
//        if (!message.hasFile()) return null;
//
//        GrpcBasicFile grpcFile = message.getFile();
//        return BasicFile.builder()
//                .fileId(grpcFile.getFileId())
//                .fileUniqueId(grpcFile.getFileUniqueId())
//                .fileSize(grpcFile.getFileSize())
//                .height(grpcFile.getHeight())
//                .width(grpcFile.getWidth())
//                .fileType(getFileType(grpcFile.getFileType()))
//                .fileExtensionType(grpcFile.getType())
//                .mimeType(grpcFile.getMimeType())
//                .emoji(grpcFile.getEmoji())
//                .fileName(grpcFile.getFileName())
//                .fileSize(grpcFile.getFileSize())
//                .duration(grpcFile.getDuration())
//                .name(grpcFile.getFileName())
//                .isAnimated(grpcFile.getIsAnimated())
//                .isVideo(grpcFile.getIsVideo())
//                .thumbnail(getPhoto(grpcFile.getThumbnail()))
//                .build();
//    }
//
//    private FileType getFileType(String fileType) {
//        if (fileType == null) return null;
//        return FileType.valueOf(fileType);
//    }
//
//    private RequestOwner getFrom(GrpcUpdateData update) {
//        if (!update.hasFrom()) return null;
//
//        GrpcUser user = update.getFrom();
//        return RequestOwner.builder()
//                .firstName(user.getFirstName())
//                .lastName(user.getLastName())
//                .userName(user.getUserName())
//                .telegramUserId(user.getUserId())
//                .languageCode(user.getLanguageCode())
//                .build();
//    }
//
//    private PollAnswer getPollAnswer(GrpcUpdateData update) {
//        if (!update.hasPollAnswer()) return null;
//
//        GrpcPollAnswer pollAnswer = update.getPollAnswer();
//        return PollAnswer.builder()
//                .pollId(pollAnswer.getPollId())
//                .optionIds(pollAnswer.getOptionIdsList())
//                .build();
//    }
//
//
//////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////
//
//
//    /**
//     * Маппинг ответа сервиса после обработки запроса в транспорт GRPC
//     *
//     * @param questResponseData - ответ сервиса
//     * @return - транспорт
//     */
//    public GrpcResponseData toGrpcResponse(QuestResponseData questResponseData) {
//        return GrpcResponseData.newBuilder()
//                .addAllMessageDataList(mapGrpcMessages(questResponseData.getMessageDataList()))
//                .build();
//    }
//
//    private List<GrpcResponseMessageData> mapGrpcMessages(List<RespMessageData> messageDataList) {
//        return messageDataList.stream()
//                .map(this::mapMessage)
//                .collect(Collectors.toList());
//    }
//
//    private GrpcResponseMessageData mapMessage(RespMessageData message) {
//        var builder = GrpcResponseMessageData.newBuilder()
//                .setMessageType(message.getMessageType().name())
//                .setChatId(message.getChatId())
//                .setProtectContent(message.isProtectContent());
//
//
//        if (message.getMessageId() != null) {
//            builder.setMessageId(message.getMessageId());
//        }
//        if (message.getText() != null) {
//            builder.setText(message.getText());
//        }
//        if (message.getEntity() != null) {
//            builder.addAllEntity(getEntityList(message.getEntity()));
//        }
//        if (message.getReplyMarkup() != null) {
//            builder.setReplyMarkup(getReplyMarkup(message.getReplyMarkup()));
//        }
//        if (message.getPollType() != null) {
//            builder.setPollType(message.getPollType().name());
//        }
//        if (message.getQuestion() != null) {
//            builder.setQuestion(message.getQuestion());
//        }
//        if (message.getOptions() != null) {
//            builder.addAllOptions(message.getOptions());
//        }
//        if (message.getCorrectOptionId() != null) {
//            builder.setCorrectOptionId(message.getCorrectOptionId());
//        }
//        if (message.getAllowMultipleAnswers() != null) {
//            builder.setAllowMultipleAnswers(message.getAllowMultipleAnswers());
//        }
//        if (message.getExplanation() != null) {
//            builder.setExplanation(message.getExplanation());
//        }
//        if (message.getExplanationEntities() != null) {
//            builder.addAllExplanationEntities(getEntityList(message.getExplanationEntities()));
//        }
//        if (message.getMediaList() != null) {
//            builder.addAllMediaList(getMediaList(message.getMediaList()));
//        }
//        if (message.getLatitude() != null) {
//            builder.setLatitude(message.getLatitude());
//        }
//        if (message.getLongitude() != null) {
//            builder.setLongitude(message.getLongitude());
//        }
//        return builder.build();
//    }
//
//    private List<GrpcSendFile> getMediaList(List<SendFile> mediaList) {
//        return mediaList.stream().map(this::getSendFile).collect(Collectors.toList());
//    }
//
//    private GrpcSendFile getSendFile(SendFile sendFile) {
//        var builder = GrpcSendFile.newBuilder()
//                .setCaption(sendFile.getCaption())
//                .setSendFileType(sendFile.getSendFileType().name())
//                .setFileUrl(sendFile.getFileUrl())
//                .setHasSpoiler(sendFile.isHasSpoiler());
//
//        if (sendFile.getCaptionEntities() != null) {
//            builder.addAllCaptionEntities(getEntityList(sendFile.getCaptionEntities()));
//        }
//        return builder.build();
//    }
//
//    private GrpcRespKeyboard getReplyMarkup(RespKeyboard replyMarkup) {
//        var builder = GrpcRespKeyboard.newBuilder()
//                .setKeyboardType(replyMarkup.getKeyboardType().name())
//                .setRemoveKeyboard(replyMarkup.isRemoveKeyboard())
//                .setResizeKeyboard(replyMarkup.isResizeKeyboard());
//
//        if (replyMarkup.getButtonRows() != null) {
//            builder.addAllButtonRows(getRowList(replyMarkup.getButtonRows()));
//        }
//        return builder.build();
//    }
//
//    private List<GrpcMessageEntityData> getEntityList(List<MessageEntityData> entity) {
//        return entity.stream()
//                .map(this::getEntity)
//                .collect(Collectors.toList());
//    }
//
//    private GrpcMessageEntityData getEntity(MessageEntityData entity) {
//        var builder = GrpcMessageEntityData.newBuilder();
//
//        if (entity.getText() != null) {
//            builder.setText(entity.getText());
//        }
//        if (entity.getEntityType() != null) {
//            if (entity.getEntityType() == MessageEntityType.TEXT_LINK) {
//                if (entity.getUrl() != null) {
//                    builder.setUrl(entity.getUrl());
//                    builder.setEntityType(entity.getEntityType().name());
//                }
//            } else {
//                builder.setEntityType(entity.getEntityType().name());
//            }
//        }
//        if (entity.getOffset() != null) {
//            builder.setOffset(entity.getOffset());
//        }
//        if (entity.getLength() != null) {
//            builder.setLength(entity.getLength());
//        }
//        return builder.build();
//    }
//
//
//    private List<GrpcButtonRow> getRowList(List<ButtonRow> buttons) {
//        return buttons.stream()
//                .map(this::getRow)
//                .collect(Collectors.toList());
//    }
//
//    private GrpcButtonRow getRow(ButtonRow buttons) {
//        return GrpcButtonRow.newBuilder()
//                .addAllButtons(getButtons(buttons.getButtons()))
//                .build();
//    }
//
//    private List<GrpcButtonData> getButtons(List<ButtonData> buttons) {
//        return buttons.stream()
//                .map(this::getButton)
//                .collect(Collectors.toList());
//    }
//
//    private GrpcButtonData getButton(ButtonData buttonData) {
//        var builder = GrpcButtonData.newBuilder()
//                .setText(buttonData.getText());
//
//        if (buttonData.getUrl() != null) {
//            builder.setUrl(buttonData.getUrl());
//        }
//        if (buttonData.getCallbackData() != null) {
//            builder.setCallbackData(buttonData.getCallbackData());
//        }
//        return builder.build();
//    }
}
