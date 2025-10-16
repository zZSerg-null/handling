package ru.zinoviev.quest.request.handler.domain.dto.response.utils;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import ru.zinoviev.quest.request.handler.domain.dto.response.KeyboardType;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseKeyboard;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseKeyboardButton;
import ru.zinoviev.quest.request.handler.domain.enums.CallbackNames;
import ru.zinoviev.quest.request.handler.domain.enums.MessageDefinition;
import ru.zinoviev.quest.request.handler.domain.jpa.QuestInfo;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class KeyboardRegistry {

    private final Map<String, KeyboardConfig> keyboards;

    public KeyboardRegistry() throws IOException {
        Yaml yaml = new Yaml();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("keyboards.yaml")) {
            if (in == null) {
                throw new IllegalStateException("Файл keyboards.yaml не найден в classpath");
            }

            Map<String, Object> yamlData = yaml.load(in);
            Map<String, Object> keyboardsData = (Map<String, Object>) yamlData.get("keyboards");

            if (keyboardsData == null) {
                throw new IllegalStateException("Неверная структура конфигурации: отсутствует ключ 'keyboards'");
            }

            this.keyboards = keyboardsData.entrySet().stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            entry -> parseKeyboardConfig(entry.getKey(), entry.getValue())
                    ));
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка инициализации KeyboardRegistry: " + e.getMessage(), e);
        }
    }

    public List<List<ResponseKeyboardButton>> getKeyboard(String name) {
        KeyboardConfig config = keyboards.get(name);

        if (config == null) {
            return null;
            //throw new IllegalArgumentException("Клавиатура '" + name + "' не найдена");
        }

        return config.buttons();
    }

    public KeyboardType getKeyboardType(String name) {
        KeyboardConfig config = keyboards.get(name);
        if (config == null) {
            throw new IllegalArgumentException("Клавиатура '" + name + "' не найдена");
        }
        return config.type();
    }

    private KeyboardConfig parseKeyboardConfig(String name, Object data) {
        try {
            Map<String, Object> keyboardData = (Map<String, Object>) data;

            // Получаем тип клавиатуры
            String typeStr = (String) keyboardData.get("type");
            KeyboardType type = KeyboardType.valueOf(typeStr);

            // Получаем строки с кнопками
            List<List<Map<String, String>>> rowsData = (List<List<Map<String, String>>>) keyboardData.get("rows");
            List<List<ResponseKeyboardButton>> buttons = new ArrayList<>();

            for (List<Map<String, String>> rowData : rowsData) {
                List<ResponseKeyboardButton> rowButtons = new ArrayList<>();

                for (Map<String, String> buttonData : rowData) {
                    String text = buttonData.get("text");
                    String callbackStr = buttonData.get("callback");
                    String webAppUrl = buttonData.get("webAppUrl");

                    String callbackData = null;
                    if (callbackStr != null) {
                        CallbackNames callbackNames = CallbackNames.valueOf(callbackStr);
                        callbackData = callbackNames.getCallbackData();
                    }

                    rowButtons.add(new ResponseKeyboardButton(text, callbackData, webAppUrl));
                }

                buttons.add(rowButtons);
            }

            return new KeyboardConfig(name, type, buttons);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Неверный CallbackType в клавиатуре '" + name + "': " + e.getMessage(), e);
        } catch (Exception e) {
            throw new IllegalStateException("Ошибка парсинга клавиатуры '" + name + "': " + e.getMessage(), e);
        }
    }

    public ResponseKeyboard buildKeyboard(List<QuestInfo> buttons, MessageDefinition definition) {
        List<List<ResponseKeyboardButton>> buttonList = new ArrayList<>();

        for (QuestInfo button : buttons) {
            List<ResponseKeyboardButton> rowButtons = new ArrayList<>();

            rowButtons.add(
                    new ResponseKeyboardButton(
                            button.getQuestName(),
                            CallbackNames.valueOf(definition.name()).getCallbackData() +
                                    CallbackNames.SEPARATOR.getCallbackData() +
                                    button.getQuestId(),
                            null)
            );

            buttonList.add(rowButtons);
        }

        return ResponseKeyboard.builder()
                .keyboardType(KeyboardType.INLINE)
                .buttons(buttonList)
                .build();
    }

    private record KeyboardConfig(String name, KeyboardType type, List<List<ResponseKeyboardButton>> buttons) {
    }
}