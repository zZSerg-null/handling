package ru.zinoviev.quest.request.handler.domain.action;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class PropertiesReader {

    private static final String BUTTONS_PROPERTIES = "button.names";
    private static final String MESSAGES_PROPERTIES = "messages.properties";

    public Map<String, String> readButtonsForRole(UserRole role) {
        return role == null ? null : readProperties(role, BUTTONS_PROPERTIES);
    }

    public Map<String, String> readMessagesForRole(UserRole role) {
        return role == null ? null : readProperties(role, MESSAGES_PROPERTIES);
    }

    private Map<String, String> readProperties(UserRole role, String filename) {
        Properties props = new Properties();
        Map<String, String> result = new HashMap<>();

        try (InputStreamReader reader = new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(filename),
                StandardCharsets.UTF_8)) {

            props.load(reader);
            for (String key : props.stringPropertyNames()) {
                if (key.startsWith(role.name().toLowerCase() + ".")) {
                    System.out.println(props.getProperty(key));
                    result.put(key, props.getProperty(key));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
