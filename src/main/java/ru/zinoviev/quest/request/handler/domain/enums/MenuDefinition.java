package ru.zinoviev.quest.request.handler.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseKeyboard;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;

@Getter
@RequiredArgsConstructor
public enum MenuDefinition {

    USER_MAIN_MENU("user_main_menu"),
    QUEST_MENU("quest_menu"),
    CREATE_NEW_QUEST_MENU("create_quest"),
    ACCOUNT_MENU("account_menu"),
    ADMIN_MAIN_MENU("admin_panel"),

    REMOVE_REPLY("remove_reply");

    private final String yamlKey;

    public String getMessage(MessageRegistry registry) {
        return registry.getMessage(yamlKey);
    }

    public ResponseKeyboard getKeyboard(KeyboardRegistry registry) {
        return ResponseKeyboard.builder()
                .buttons(registry.getKeyboard(yamlKey))
                .keyboardType(registry.getKeyboardType(yamlKey))
                .build();
    }

}
