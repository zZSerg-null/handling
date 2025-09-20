package ru.zinoviev.quest.request.handler.domain.action;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.DispatchKey;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.response.SendMessageData;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class FallbackDispatcher extends ActionDispatcher {

    @Value("${dispatching.unknown-request:IGNORING}")
    private String reactionType;

    public FallbackDispatcher(ResponsePublisher publisher, PropertiesReader propertiesReader) {
        super(publisher, propertiesReader);
    }

    @Override
    public void dispatch(RequestData request) {
        AnsiConsole.println("Сообщение, не имеющее обработчика: " + request, AnsiConsole.BrightColor.CYAN);

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
