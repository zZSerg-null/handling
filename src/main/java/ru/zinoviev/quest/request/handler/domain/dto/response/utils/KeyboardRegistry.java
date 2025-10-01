package ru.zinoviev.quest.request.handler.domain.dto.response.utils;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import ru.zinoviev.quest.request.handler.domain.dto.response.KeyboardButton;
import ru.zinoviev.quest.request.handler.domain.dto.response.KeyboardType;
import ru.zinoviev.quest.request.handler.domain.enums.CallbackNames;

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

    public List<List<KeyboardButton>> getKeyboard(String name) {
        KeyboardConfig config = keyboards.get(name);
        if (config == null) {
            throw new IllegalArgumentException("Клавиатура '" + name + "' не найдена");
        }
        return config.getButtons();
    }

    public KeyboardType getKeyboardType(String name) {
        KeyboardConfig config = keyboards.get(name);
        if (config == null) {
            throw new IllegalArgumentException("Клавиатура '" + name + "' не найдена");
        }
        return config.getType();
    }

    private KeyboardConfig parseKeyboardConfig(String name, Object data) {
        try {
            Map<String, Object> keyboardData = (Map<String, Object>) data;

            // Получаем тип клавиатуры
            String typeStr = (String) keyboardData.get("type");
            KeyboardType type = KeyboardType.valueOf(typeStr);

            // Получаем строки с кнопками
            List<List<String>> rowsData = (List<List<String>>) keyboardData.get("rows");
            List<List<KeyboardButton>> buttons = new ArrayList<>();

            for (List<String> rowData : rowsData) {
                List<KeyboardButton> rowButtons = new ArrayList<>();

                if (type == KeyboardType.INLINE) {
                    // Для INLINE клавиатур: пары [текст, callbackType]
                    for (int i = 0; i < rowData.size(); i += 2) {
                        String text = rowData.get(i);
                        String callbackTypeStr = rowData.get(i + 1);
                        CallbackNames callbackNames = CallbackNames.valueOf(callbackTypeStr);
                        rowButtons.add(new KeyboardButton(text, callbackNames.getCallbackData()));
                    }

                    // Для REPLY клавиатур: только текст, callbackType = null
                } else {
                    for (String text : rowData) {
                        rowButtons.add(new KeyboardButton(text, null));
                    }
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

    @Data
    private static class KeyboardConfig {
        private final String name;
        private final KeyboardType type;
        private final List<List<KeyboardButton>> buttons;
    }

}