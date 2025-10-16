package ru.zinoviev.quest.request.handler.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseKeyboard;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;

@Getter
@RequiredArgsConstructor
public enum MessageDefinition {

    USER_MAIN_MENU("user_main_menu"),
    QUEST_MENU("quest_menu"),
    USER_QUESTS("quest_list"),
    USER_QUESTS_EMPTY("quest_list_empty"),
    USER_RUN_QUEST("run_list"),
    USER_RUN_SELECTED("run_selected"),
    USER_RUNNING_QUESTS("running_list"),
    USER_RUNNING_QUESTS_EMPTY("running_list_empty"),
    CREATE_NEW_QUEST("create_new_quest"),
    START_CREATION("start_creation"),
    START_CREATION_EDIT("start_creation_edit"),
    ACCOUNT_MENU("account_menu"),







    ADMIN_MAIN_MENU("admin_panel"),

    REMOVE_REPLY("remove_reply");

    private final String yamlKey;

    public String getMessage(MessageRegistry registry) {
        return registry.getMessage(yamlKey);
    }

    public ResponseKeyboard getKeyboard(KeyboardRegistry registry) {
        var keyboard = registry.getKeyboard(yamlKey);

        if (keyboard == null){
            return null;
        }

        return ResponseKeyboard.builder()
                .buttons(keyboard)
                .keyboardType(registry.getKeyboardType(yamlKey))
                .build();
    }

}
