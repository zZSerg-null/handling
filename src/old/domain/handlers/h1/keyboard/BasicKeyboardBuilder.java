package ru.old.domain.handlers.h1.keyboard;

import old.domain.dto.response.keyboard.ButtonData;
import old.domain.dto.response.keyboard.ButtonRow;
import old.domain.dto.response.keyboard.RespKeyboard;
import ru.old.domain.handlers.roles.constants.CallbackConstants;
import org.springframework.stereotype.Component;
import old.domain.enums.KeyboardType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BasicKeyboardBuilder {

    protected RespKeyboard.RespKeyboardBuilder getInlineKeyboardBuilder() {
        return RespKeyboard.builder()
                .keyboardType(KeyboardType.INLINE)
                .resizeKeyboard(true);
    }

    protected RespKeyboard.RespKeyboardBuilder getReplyKeyboardBuilder() {
        return RespKeyboard.builder()
                .keyboardType(KeyboardType.REPLY_ADD)
                .resizeKeyboard(true);
    }

    protected List<ButtonRow> getButtons(List<String[]> buttonsArray) {
        List<ButtonRow> buttonRows = new ArrayList<>();

        for (String[] row : buttonsArray) {
            if (row.length % 2 != 0) throw new RuntimeException("Количество кнопок не равно количеству колбэков");

            List<ButtonData> buttonsOnRow = new ArrayList<>();
            for (int i = 0; i < row.length; i += 2) {
                buttonsOnRow.add(new ButtonData(row[i], row[i + 1]));
            }
            buttonRows.add(ButtonRow.builder()
                    .buttons(buttonsOnRow)
                    .build());
        }
        return buttonRows;
    }

    protected List<ButtonRow> getDynamicButtons(Map<String, String> nodeMap) {
        List<ButtonRow> buttonRows = new ArrayList<>();

        for (Map.Entry<String, String> entry : nodeMap.entrySet()) {
            List<ButtonData> brow1 = new ArrayList<>();

            brow1.add(
                    new ButtonData(entry.getValue(), CallbackConstants.EDIT_NODE_SELECT_NODE +":"+ entry.getKey())
            );
            var row1 = ButtonRow.builder()
                    .buttons(brow1)
                    .build();

            List<ButtonData> brow2 = new ArrayList<>();
            brow2.addAll( List.of(
                            new ButtonData("\uD83D\uDD3A выше", CallbackConstants.EDIT_NODE_SELECT_NODE +":"+ entry.getKey()),
                            new ButtonData("\uD83D\uDD3B ниже", CallbackConstants.EDIT_NODE_SELECT_NODE +":"+ entry.getKey()),
                            new ButtonData("❌ удалить", CallbackConstants.EDIT_NODE_SELECT_NODE +":"+ entry.getKey())
                    )
            );
            var row2 = ButtonRow.builder()
                    .buttons(brow2)
                    .build();

            buttonRows.add(row1);
            buttonRows.add(row2);
        }

        return buttonRows;
    }

}
