package ru.zinoviev.quest.request.handler.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class TelegramLocation extends TelegramRequest {

    private final Double longitude;
    private final Double latitude;

    @Builder
    public TelegramLocation(Long userId, String userName, String firstName, String lastName, Integer messageId,
                            Double longitude, Double latitude) {

        super(userId, userName, firstName, lastName, messageId);
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
