package ru.zinoviev.quest.request.handler.domain.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.KeyboardRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.MessageRegistry;
import ru.zinoviev.quest.request.handler.domain.dto.response.utils.ResponseFactory;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class FallbackDispatcher extends ActionDispatcher {

    @Value("${dispatching.unknown-request:IGNORING}")
    private String reactionType;

    public FallbackDispatcher(ResponseFactory responseFactory, ResponsePublisher publisher, KeyboardRegistry keyboardRegistry, MessageRegistry messageRegistry) {
        super(responseFactory, publisher, keyboardRegistry, messageRegistry);
    }

    @Override
    public void dispatch(RequestData request) {
        AnsiConsole.println("Сообщение, не имеющее обработчика для текущей роли: " + request, AnsiConsole.BrightColor.CYAN);

        if (reactionType.equalsIgnoreCase("EXCEPTION")){
            throw new UnhandableRequestTypeException("Событие не может быть обработано");

        } else if (reactionType.equalsIgnoreCase("SEND_MESSAGE")){
            sendResponse(SendMessageData.builder().build());
        }
    }

    @Override
    public DispatchKey key() {
        return null; // ничего не возвращаем, никак не обрабатываем.
    }

    @Override
    public UserRole getRole() {
        return null;
    }


}
