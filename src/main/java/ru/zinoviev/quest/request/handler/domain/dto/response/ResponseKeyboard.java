package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.*;

import java.util.List;


/**
 * Клавиатура, отправляемая пользователю
 * может быть  трех разных типов: REPLY_ADD, REPLY_REMOVE, INLINE
 * INLINE - имеет колбэки, все остальные нет
 *
 * все клавиатуры заранее предопределены в конфиге keyboards.yaml,
 * и собираются в KeyboardRegistry для последующего использования
 */
@Getter
@Builder
@ToString
public class ResponseKeyboard {

    private KeyboardType keyboardType;
    private List<List<ResponseKeyboardButton>> buttons;

}
