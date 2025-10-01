package ru.zinoviev.quest.request.handler.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.dto.response.Keyboard;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;

@Getter
@RequiredArgsConstructor
public enum MenuDefinition {

    USER_MAIN_MENU("user_main_menu"),
    QUEST_MENU("quest_menu"),
    ACCOUNT_MENU("account_menu"),
    ADMIN_MAIN_MENU("admin_panel");

    private final String yamlKey;

    public String getMessage(MessageRegistry registry) {
        return registry.getMessage(yamlKey);
    }

    public Keyboard getKeyboard(KeyboardRegistry registry) {
        return Keyboard.builder()
                .buttons(registry.getKeyboard(yamlKey))
                .keyboardType(registry.getKeyboardType(yamlKey))
                .build();
    }

}
