package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.lang.Nullable;

/**
 * класс обертка для кнопок клавиатур Может иметь и не иметь колбэков в зависимости
 * от типа клавиатуры.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class KeyboardButton {

    private final String name;
    private final String callbackData;
    private String webAppUrl;

}
