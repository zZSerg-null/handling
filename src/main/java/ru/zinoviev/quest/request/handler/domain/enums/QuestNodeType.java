package ru.zinoviev.quest.request.handler.domain.enums;

public enum QuestNodeType {

    MESSAGE("Текст\\файл"),
    POLL("Опрос"),
    GEO("Гео точка"),
    MEDIA("Файлы");

    private final String displayName;

    QuestNodeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
