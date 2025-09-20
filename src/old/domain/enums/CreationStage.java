package ru.old.domain.enums;

public enum CreationStage {
    NODE_MENU_HELP,

    UNSAVED_NODE_DATA,


    NEW_QUEST_AWATING_NAME_CONFIRMATION,
    MAIN_MENU_NODE_TYPE_SELECTION,

    /**
     * Пользователем может быть выбрана для создания одна из 4-х нод
     */
    NODE_MENU_MESSAGE,
    NODE_MENU_POLL,
    NODE_MENU_GEO,
    NODE_MENU_MEDIAGROUP,



    /**
     * Этапы заполнения нод
     */
    ADDING_TEXT,
    ADDING_FILE, // добавление файла к ноде (назад - меню MESSAGE_NODE_CREATION)




    ADDING_GEOPOINT, // добавление локации (назад - меню создания геоточки)
    SUBMENU_GEOPOINT_RADIUS_MENU,
    ADDING_GEOPOINT_RADIUS_VALUE,
    ADDING_GEOPOINT_RADIUS_MESSAGES,


    SUBMENU_EXPECTED_ANSWERS_MENU,
    ADDING_EXPECTED_ANSWERS,
    ADDING_EXPECTED_ANSWERS_COUNT,
    ADDING_CORRECT_MESSAGE_REACT,
    ADDING_WRONG_MESSAGE_REACT,





    ADDING_POLL_NODE_QUESTION,
    ADDING_POLL_NODE_ANSWERS,
    ADDING_POLL_NODE_EXPLANATION,

    ADDING_MEDIA_GROUP,


    EDIT_NODE_VIEW_LIST,


    STOP_CREATING,


    COMPLETE



}
