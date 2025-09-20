package ru.old.domain.handlers.roles.impl.user;

import lombok.RequiredArgsConstructor;
import old.domain.dto.response.keyboard.RespKeyboard;
import org.springframework.stereotype.Component;
import old.domain.enums.CreationStage;
import old.domain.handlers.h1.keyboard.CreatorKeyboardsKeeper;

@Component
@RequiredArgsConstructor
public class UserMenuSelector {

    protected final CreatorKeyboardsKeeper creatorKeyboardsKeeper;

    public RespKeyboard getMenuByStage(CreationStage stage) {
        return null;
    }
}
