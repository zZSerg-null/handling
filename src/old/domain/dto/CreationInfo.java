package ru.old.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.old.domain.enums.CreationStage;

import java.util.LinkedList;

@Getter
@Setter
@Builder
@ToString
public class CreationInfo {
    private LinkedList<CreationStage> creationStageList;
    private Long creationId;
    private String questName;
    private Long questId;
    private MenuNodeInfo menuNodeInfo;
    private boolean mainFieldsEmpty;
}
