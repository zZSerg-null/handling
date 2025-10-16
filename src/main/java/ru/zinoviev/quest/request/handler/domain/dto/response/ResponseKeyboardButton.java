package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.*;

/**
 * класс обертка для кнопок клавиатур Может иметь и не иметь колбэков в зависимости
 * от типа клавиатуры.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
public class ResponseKeyboardButton {

    private final String name;
    private final String callbackData;
    private final String webAppUrl;

}
