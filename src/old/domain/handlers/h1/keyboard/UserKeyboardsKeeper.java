package ru.old.domain.handlers.h1.keyboard;

import old.domain.dto.response.keyboard.RespKeyboard;
import ru.old.domain.handlers.roles.constants.CallbackConstants;
import org.springframework.stereotype.Component;
import old.domain.dto.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserKeyboardsKeeper extends BasicKeyboardBuilder {




    private final List<String[]> USER_QUEST_MAIN_MENU = List.of(
            new String[]{"Мои квесты", CallbackConstants.USER_QUEST_MENU_SHOW_ALL},
            new String[]{"Запущенные квесты", CallbackConstants.USER_QUEST_MENU_RUNNING},
            new String[]{"Создать квест", CallbackConstants.USER_QUEST_MENU_CREATE}
    );

    private final List<String[]> USER_ACCOUNT_MAIN_MENU = List.of(
            new String[]{"Статистика", CallbackConstants.USER_ACCOUNT_MENU_STATISTIC},
            new String[]{"Сменить имя", CallbackConstants.USER_ACCOUNT_MENU_CHANGE_NAME}
    );

    private final List<String[]> USER_CHANGING_NAME_CONFIRMATION_MENU = List.of(
            new String[]{"Да, хочу", CallbackConstants.USER_CHANGE_NAME_CONFIRM},
            new String[]{"Все таки, наверное, нет", CallbackConstants.CREATION_MENU_BACK_BUTTON}
    );




    // Меню квестов
    public RespKeyboard getUserQuestMenuKeyboard(UserInfo userInfo) {
        List<String[]> menu = new ArrayList<>();

        //составляем меню в зависимости от информации. Если квестов нет или нет запущенных - не показывает кнопки
        for (String[] row : USER_QUEST_MAIN_MENU) {
            if ((row[0].equals("Мои квесты")) && userInfo.getUserQuestsInfo().getQuestNames().isEmpty()) {
                continue;
            }
            if (row[0].equals("Запущенные квесты") && userInfo.getUserQuestsInfo().getRunningQuestCount() == 0) {
                continue;
            }
            menu.add(row);
        }
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(menu))
                .build();
    }


    public RespKeyboard getUserAccountMenuKeyboard() {
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(USER_ACCOUNT_MAIN_MENU))
                .build();
    }


    public RespKeyboard getUserChangingNameKeyboard() {
        return getInlineKeyboardBuilder()
                .buttonRows(getButtons(USER_CHANGING_NAME_CONFIRMATION_MENU))
                .build();
    }
}
