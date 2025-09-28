package ru.old.rest.dto.request;

import lombok.*;
import old.rest.dto.request.message.RestBasicFile;
import old.rest.dto.request.message.RestLocation;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestMessage {
    private Integer messageId;
    private boolean isEditedMessage;
    private Long chatId;
    private Integer date;
    private String text;
    private RestBasicFile file;
    private Boolean hasMediaSpoiler;
    private String mediaGroupId;
    private String caption;
    private RestLocation location;
    private Boolean successfulPayment;
}
