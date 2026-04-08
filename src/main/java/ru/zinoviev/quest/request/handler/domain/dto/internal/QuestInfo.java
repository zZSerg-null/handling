package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class QuestInfo {
    private Long questId;
    private String questName;
}
