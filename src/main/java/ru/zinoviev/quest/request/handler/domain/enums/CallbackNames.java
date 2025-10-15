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
    QUEST_MENU("main_menu"),
    CREATE_NEW_QUEST("create_quest"),
    START_CREATION("start_quest_creation"),
    START_QUEST("start_quest"),
    QUEST_LIST("quest_list"),
    USER_QUEST("user_quest"),
    RUNNING("running_list"),
    ACCOUNT("account"),
    MY_STATISTIC("my_stat"),
    WEBAPP("run_web_app"),



    /**
     * ADMIN
     */
    ADMIN_USERS("admin_users"),
    ADMIN_MANAGEMENT("admin_management"),
    ADMIN_BANLIST("admin_banlist"),
    ADMIN_CONFIGLIST("admin_configlist"),








    /**
     * PLAYER
     */




    /**
     * CREATOR
     */


    /**
     * ALL ROLES
     */
    SEPARATOR(":"),
    BACK("step_back");

    private final String data;

    CallbackNames(String data) {
        this.data = data;
    }

    public String getCallbackData() {
        return data;
    }

}
