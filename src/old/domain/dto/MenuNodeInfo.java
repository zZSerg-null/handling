package ru.old.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MenuNodeInfo {

    private Long nodeId;

    private boolean mainText;
    private boolean expectedUserAnswers;
    private boolean expectedAnswersCount;
    private boolean correctAnswersReactMessages;
    private boolean wrongAnswersReactMessages;
    private boolean geoPoint;
    private boolean pollAnswers;
    private boolean correctPollOptionId;
    private boolean explanation;
    private boolean mediaList;
    private boolean geoActiveRadius;
    private boolean radiusMessage;
}
