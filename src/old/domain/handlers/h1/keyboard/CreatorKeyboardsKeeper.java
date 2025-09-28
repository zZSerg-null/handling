package ru.old.domain.handlers.h1.keyboard;

import old.domain.dto.response.keyboard.ButtonRow;
import old.domain.dto.response.keyboard.RespKeyboard;
import ru.old.domain.handlers.roles.constants.CallbackConstants;
import ru.old.domain.handlers.roles.constants.MessageConstants;
import org.springframework.stereotype.Component;
import old.domain.dto.MenuNodeInfo;
import old.domain.enums.KeyboardType;

import java.util.List;
import java.util.Map;

@Component
public class CreatorKeyboardsKeeper extends BasicKeyboardBuilder {

    private final List<String[]> QUEST_CREATION_CONFIRM_QUEST_NAME = List.<String[]>of(
            new String[]{"Оставить это название", CallbackConstants.QUEST_CREATION_ACCEPT_NAME},
            new String[]{"Подумаю еще", CallbackConstants.QUEST_CREATION_CONTINUE}
    );

    private final List<String[]> STOP_CREATION_MENU = List.of(
            new String[]{"Да, хочу", CallbackConstants.QUEST_CREATION_STOP},
            new String[]{"Нет, продолжу", CallbackConstants.QUEST_CREATION_CONTINUE}
    );

    private final List<String[]> CREATOR_CREATE_QUEST_TYPE_SELECTION_MENU = List.of(
            new String[]{"Простое сообщение", CallbackConstants.NODE_MENU_MESSAGE, "Сообщение - опрос", CallbackConstants.NODE_MENU_POLL},
            new String[]{"Геопозиция", CallbackConstants.NODE_MENU_GEO, "Группа файлов", CallbackConstants.NODE_MENU_MEDIAGROUP},
            new String[]{"Изменить созданные", CallbackConstants.EDIT_NODE_VIEW_LIST},
            new String[]{"Завершить создание", CallbackConstants.QUEST_CREATION_STOP},
            new String[]{"❓ Справка", CallbackConstants.NODE_MENU_HELP}
    );

    private final List<String[]> ACTIVE_RADIUS_MENU = List.of(
            new String[]{"Задать расстояние", CallbackConstants.ADDING_GEOPOINT_RADIUS_VALUE},
            new String[]{"Текст оповещения", CallbackConstants.ADDING_GEOPOINT_RADIUS_MESSAGES},

            new String[]{"Назад", CallbackConstants.CREATION_MENU_BACK_BUTTON}
    );

    private final List<String[]> DROP_UNSAVED_DATA = List.of(
            new String[]{"Да, хочу", CallbackConstants.DELETE_NODE},
            new String[]{"Нет конечно!", CallbackConstants.CONTINUE_NODE_CREATION}
    );

    private final List<String[]> EDIT_KEYBOARD_BACK_BUTTON = List.<String[]>of(
            new String[]{"Назад", CallbackConstants.EDIT_NODE_BACK_BUTTON}
    );


    // НЕ Inline КЛАВИАТУРА !
    private final List<String[]> CREATOR_REPLY_KEYBOARD = List.<String[]>of(
            new String[]{"Сохранить", "foo"},
            new String[]{"Удалить все", "bar"}
    );

    public RespKeyboard activeRadiusKeyboard() {

        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(ACTIVE_RADIUS_MENU))
                .build();
    }

    public RespKeyboard expectedAnswersKeyboard(MenuNodeInfo menuNodeInfo) {
        List<String[]> expectedAnswersMenu = List.of(
                new String[]{"Добавить ожидаемые ответы", CallbackConstants.ADDING_EXPECTED_ANSWERS},
                new String[]{"Указать требуемое количество", CallbackConstants.ADDING_EXPECTED_ANSWERS_COUNT},
                new String[]{"Добавить реакции на верный ответ", CallbackConstants.ADDING_CORRECT_ANSWERS_REACTIONS},
                new String[]{"Добавить реакции на неверный ответ", CallbackConstants.ADDING_WRONG_ANSWERS_REACTIONS},

                new String[]{"Назад", CallbackConstants.CREATION_MENU_BACK_BUTTON}
        );


        if (menuNodeInfo.isMainText()) {
            expectedAnswersMenu.get(0)[0] = MessageConstants.DATA_WAS_SET +" "+ expectedAnswersMenu.get(0)[0];
        } else expectedAnswersMenu.get(0)[0] = MessageConstants.EMPTY_FIELD +" "+ expectedAnswersMenu.get(0)[0];

        if (menuNodeInfo.isExpectedAnswersCount()) {
            expectedAnswersMenu.get(1)[0] = MessageConstants.DATA_WAS_SET +" "+ expectedAnswersMenu.get(1)[0];
        } else if (menuNodeInfo.isCorrectAnswersReactMessages()) {
            expectedAnswersMenu.get(2)[0] = MessageConstants.DATA_WAS_SET +" "+ expectedAnswersMenu.get(2)[0];
        } else if (menuNodeInfo.isWrongAnswersReactMessages()) {
            expectedAnswersMenu.get(2)[0] = MessageConstants.DATA_WAS_SET +" "+ expectedAnswersMenu.get(2)[0];
        }

        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(expectedAnswersMenu))
                .build();
    }

    public RespKeyboard stopCreationKeyboard() {
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(STOP_CREATION_MENU))
                .build();
    }

    public RespKeyboard messageNodeMenuKeyboard(MenuNodeInfo menuNodeInfo) {
        List<String[]> simpleMessageMenu = List.of(
                new String[]{"Добавить текст", CallbackConstants.ADDING_TEXT},
                new String[]{"Прикрепить файл", CallbackConstants.ADDING_MESSAGE_NODE_FILE},
                new String[]{"Ожидаемые ответы", CallbackConstants.SUBMENU_EXPECTED_ANSWERS},

                new String[]{"Сохранить и вернуться", CallbackConstants.SAVE_NODE_DATA}
        );

        if (menuNodeInfo == null) {
            return getInlineKeyboardBuilder()
                    .buttonRows(getButtons(simpleMessageMenu))
                    .build();
        }

        if (menuNodeInfo.isMainText()) {
            simpleMessageMenu.get(0)[0] = MessageConstants.DATA_WAS_SET +" "+ simpleMessageMenu.get(0)[0];
        } else simpleMessageMenu.get(0)[0] = MessageConstants.EMPTY_FIELD +" "+ simpleMessageMenu.get(0)[0];

        if (menuNodeInfo.isMediaList()) {
            simpleMessageMenu.get(1)[0] = MessageConstants.DATA_WAS_SET +" "+ simpleMessageMenu.get(1)[0];
        } else simpleMessageMenu.get(1)[0] = MessageConstants.EMPTY_FIELD +" "+ simpleMessageMenu.get(1)[0];

        if (menuNodeInfo.isExpectedUserAnswers()) {
            simpleMessageMenu.get(2)[0] = MessageConstants.DATA_WAS_SET +" "+ simpleMessageMenu.get(2)[0];
        }

        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(simpleMessageMenu))
                .build();
    }

    public RespKeyboard pollNodeMenuKeyboard(MenuNodeInfo menuNodeInfo) {
        List<String[]> pollMenu = List.of(
                new String[]{"Вопрос", CallbackConstants.ADDING_POLL_NODE_QUESTION},
                new String[]{"Варианты ответов", CallbackConstants.ADDING_POLL_NODE_ANSWERS},
                new String[]{"Объяснение", CallbackConstants.ADDING_POLL_NODE_EXPLANATION},

                new String[]{"Сохранить и вернуться", CallbackConstants.SAVE_NODE_DATA}
        );

        if (menuNodeInfo == null) {
            return getInlineKeyboardBuilder()
                    .buttonRows(getButtons(pollMenu))
                    .build();
        }

        if (menuNodeInfo.isMainText()) {
            pollMenu.get(0)[0] = MessageConstants.DATA_WAS_SET +" "+ pollMenu.get(0)[0];
        } else pollMenu.get(0)[0] = MessageConstants.EMPTY_FIELD +" "+ pollMenu.get(0)[0];

        if (menuNodeInfo.isPollAnswers()) {
            pollMenu.get(1)[0] = MessageConstants.DATA_WAS_SET +" "+ pollMenu.get(1)[0];
        } else pollMenu.get(1)[0] = MessageConstants.EMPTY_FIELD +" "+ pollMenu.get(1)[0];

        if (menuNodeInfo.isExplanation()) {
            pollMenu.get(2)[0] = MessageConstants.DATA_WAS_SET +" "+ pollMenu.get(2)[0];
        }

        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(pollMenu))
                .build();
    }

    public RespKeyboard geoNodeMenuKeyboard(MenuNodeInfo menuNodeInfo) {
        List<String[]> geopointMenu = List.of(
                new String[]{"Указать геопозицию", CallbackConstants.ADDING_GEOPOINT},
                new String[]{"Указать радиус активности", CallbackConstants.SUBMENU_GEO_RADIUS},
                new String[]{"Ожидаемые ответы", CallbackConstants.SUBMENU_EXPECTED_ANSWERS},

                new String[]{"Сохранить и вернуться", CallbackConstants.SAVE_NODE_DATA}
        );
        if (menuNodeInfo == null) {
            return getInlineKeyboardBuilder()
                    .buttonRows(getButtons(geopointMenu))
                    .build();
        }

        if (menuNodeInfo.isGeoPoint()) {
            geopointMenu.get(0)[0] = MessageConstants.DATA_WAS_SET +" "+ geopointMenu.get(0)[0];
        } else geopointMenu.get(0)[0] = MessageConstants.EMPTY_FIELD +" "+geopointMenu.get(0)[0];

        if (menuNodeInfo.isGeoActiveRadius()) {
            geopointMenu.get(1)[0] = MessageConstants.DATA_WAS_SET +" "+ geopointMenu.get(1)[0];
        } else if (menuNodeInfo.isExpectedUserAnswers()) {
            geopointMenu.get(2)[0] = MessageConstants.DATA_WAS_SET +" "+ geopointMenu.get(2)[0];
        }
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(geopointMenu))
                .build();
    }

    public RespKeyboard mediaGroupMenuKeyboard(MenuNodeInfo menuNodeInfo) {
        List<String[]> multimediaMenu = List.of(
                new String[]{"Прикрепить файлы", CallbackConstants.ADDING_MEDIA_GROUP},
                new String[]{"Ожидаемые ответы", CallbackConstants.SUBMENU_EXPECTED_ANSWERS},
                new String[]{"Сохранить и вернуться", CallbackConstants.SAVE_NODE_DATA}
        );

        if (menuNodeInfo == null){
            return getInlineKeyboardBuilder()
                    .buttonRows(getButtons(multimediaMenu))
                    .build();
        }

        if (menuNodeInfo.isMediaList()) {
            multimediaMenu.get(0)[0] = MessageConstants.DATA_WAS_SET +" "+ multimediaMenu.get(0)[0];
        } else multimediaMenu.get(0)[0] = MessageConstants.DATA_WAS_SET +" "+ multimediaMenu.get(0)[0];

        if (menuNodeInfo.isExpectedUserAnswers()) {
             multimediaMenu.get(1)[0] = MessageConstants.DATA_WAS_SET +" "+ multimediaMenu.get(1)[0];
        }

        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(multimediaMenu))
                .build();
    }

    public RespKeyboard getNodeTypeSelectionKeyboard() {
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(CREATOR_CREATE_QUEST_TYPE_SELECTION_MENU))
                .build();
    }

    public RespKeyboard getQuestCreationConfirmNameKeyboard() {
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(QUEST_CREATION_CONFIRM_QUEST_NAME))
                .build();
    }

    public RespKeyboard creatorReplyAddKeyboard() {
        return RespKeyboard.builder()
                .keyboardType(KeyboardType.REPLY_ADD)
                .resizeKeyboard(true)
                .buttonRows(getButtons(CREATOR_REPLY_KEYBOARD))
                .build();
    }

    public RespKeyboard creatorReplyRemoveKeyboard() {
        return RespKeyboard.builder()
                .keyboardType(KeyboardType.REPLY_REMOVE)
                .build();
    }

    public RespKeyboard dropUnsavedDataKeyboard() {
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(DROP_UNSAVED_DATA))
                .build();
    }

    public RespKeyboard getDynamicNodeEditKeyboard(Map<String, String> nodeMap) {
        List<ButtonRow> nodes = getDynamicButtons(nodeMap);

        nodes.addAll(getButtons(EDIT_KEYBOARD_BACK_BUTTON));
        return getInlineKeyboardBuilder()
                .resizeKeyboard(false)
                .buttonRows(nodes)
                .build();
    }
}
