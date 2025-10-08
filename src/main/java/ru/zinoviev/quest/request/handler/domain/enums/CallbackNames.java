package ru.zinoviev.quest.request.handler.domain.enums;

/**
 * Перечисление колбэков для работы приложения.
 * Используются по большей части в CallbackActionDispatcher-ах
 *
 * ! Имена используемых колбов должны быть идентичны значениям в конфигурации клавиатур - keyboards.yaml
 * (или наоборот)
 *
 * При загрузке имена сравниваются, неучтенные колбэки в этом классе останутся без внимания,
 * Колбэки из конфигурационного файла, которым не нашлось совпадения здесь - выбросят исключение.
 */
public enum CallbackNames {
    /**
     * USER
     */
    QUEST_MENU,
    CREATE_QUEST,
    START_QUEST,
    QUEST_LIST,
    RUNNING,
    ACCOUNT,
    MY_STATISTIC,
    WEBAPP, //go webApp, no callback handling



    /**
     * ADMIN
     */
    ADMIN_USERS,
    ADMIN_MANAGEMENT,
    ADMIN_BANLIST,
    ADMIN_CONFIGLIST,


    /**
     * PLAYER
     */




    /**
     * CREATOR
     */


    /**
     * ALL ROLES
     */
    BACK;

    public String getCallbackData() {
        return name().toLowerCase();
    }

    public static CallbackNames fromString(String data) {
        return valueOf(data.toUpperCase());
    }
}
