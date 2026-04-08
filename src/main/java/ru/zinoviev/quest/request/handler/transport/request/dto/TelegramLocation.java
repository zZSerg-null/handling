package ru.zinoviev.quest.request.handler.transport.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public final class TelegramLocation extends TelegramRequest {

    @NotNull
    private final Double longitude;

    @NotNull
    private final Double latitude;

    @Builder
    public TelegramLocation(Long userId, String userName, Integer messageId, Double longitude, Double latitude) {

        super(userId, userName, messageId);
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
