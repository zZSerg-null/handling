package ru.old.domain.dto.request.message;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestLocation {
    private Double longitude;
    private Double latitude;
    private Integer livePeriod;
}
