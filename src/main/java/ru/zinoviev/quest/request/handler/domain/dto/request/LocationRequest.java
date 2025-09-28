package ru.zinoviev.quest.request.handler.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

@Getter
@ToString
public final class LocationRequest extends RequestData {

    private final Double longitude;
    private final Double latitude;

    @Builder
    public LocationRequest(Long telegramId, String userName, Double longitude, Double latitude) {
        super(telegramId, userName);
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public RequestType getType() {
        return RequestType.LOCATION;
    }
}
