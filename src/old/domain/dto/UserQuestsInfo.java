package ru.old.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserQuestsInfo {
    private int runningQuestCount;
    private List<String> questNames;
}
