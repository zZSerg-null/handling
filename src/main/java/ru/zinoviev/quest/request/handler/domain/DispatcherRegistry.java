package ru.zinoviev.quest.request.handler.domain;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.action.FallbackDispatcher;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DispatcherRegistry {

    private final Map<DispatchKey, ActionDispatcher> registry;
    private final FallbackDispatcher fallback;

    public DispatcherRegistry(List<ActionDispatcher> dispatchers, FallbackDispatcher fallback) {
        this.fallback = fallback;
        this.registry = dispatchers.stream()
                .peek(ActionDispatcher::loadMessagesAndButtons)
                .collect(
                        Collectors.toMap(
                                ActionDispatcher::key, disp -> disp
                        )
                );
    }

    public ActionDispatcher get(UserRole role, RequestType type) {
        return registry.getOrDefault(new DispatchKey(role, type), fallback);
    }


}
