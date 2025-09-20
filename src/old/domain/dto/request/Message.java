package ru.old.domain.dto.request;

import lombok.*;

import old.domain.dto.request.message.BasicFile;
import old.domain.dto.request.message.Location;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    private Integer messageId;
    private boolean isEditedMessage;
    private Long chatId;
    private Integer date;
    private String text;
    private BasicFile file;
    private Boolean hasMediaSpoiler;
    private String mediaGroupId;
    private String caption;
    private Location location;
    private Boolean successfulPayment;
}
