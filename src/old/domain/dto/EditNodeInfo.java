package ru.old.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.old.domain.enums.QuestNodeType;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class EditNodeInfo {

    private Long nodeId;
    private QuestNodeType type;

    private String mainText;
    private boolean geoPointPresent;
    private boolean pollAnswersPresent;
    private boolean correctPollOptionIdPresent;
    private List<BasicFileInfo> mediaList;
}
