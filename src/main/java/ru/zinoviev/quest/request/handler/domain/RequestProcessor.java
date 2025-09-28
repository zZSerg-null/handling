package ru.zinoviev.quest.request.handler.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Component
@RequiredArgsConstructor
public class RequestProcessor {

    private final DispatcherRegistry registry;

    public void process(UserRole role, RequestType type, RequestData data) {
        ActionDispatcher dispatcher = registry.get(role, type);
        dispatcher.dispatch(data);
    }

}
