package ru.old.domain.dto.request.message;

import lombok.Builder;

@Builder
public record Location(Double longitude, Double latitude, Integer livePeriod) {
}
