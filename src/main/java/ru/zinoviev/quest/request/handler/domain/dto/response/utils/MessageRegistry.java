package ru.zinoviev.quest.request.handler.domain.dto.response.utils;

import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class MessageRegistry {

    private Map<String, String> messageMap;

    public MessageRegistry() {
        Yaml yaml = new Yaml();

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("messages.yaml")) {
            if (input == null) {
                throw new IllegalStateException("Файл messages.yaml не найден в classpath");
            }
            Map<String, Object> messages = yaml.load(input); //все руты ямла
            messageMap = (Map<String, String>) messages.get("messages"); //конкретный рут
        } catch (IOException e) {
            throw new IllegalStateException("Ошибка инициализации MessageRegistry: " + e.getMessage(), e);
        }
    }

    public String getMessage(String messageName) {
        return escapeMarkdownV2(messageMap.getOrDefault(messageName, "Сообщение не найдено"));
    }

    private String escapeMarkdownV2(String text) {
        if (text == null) return "";
        return text.replaceAll("([-+\\\\=()#!{}.])", "\\\\$1");
    }
}
