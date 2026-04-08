package ru.zinoviev.quest.request.handler.domain;

import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.BasicActionHandler;
import ru.zinoviev.quest.request.handler.domain.action.UnexpectedRequestTypeDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.transport.protocol.AnsiConsole;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class DispatcherRegistry {

    private final Map<DispatchKey, BasicActionHandler> registry;
    private final UnexpectedRequestTypeDispatcher fallback;

    public DispatcherRegistry(List<BasicActionHandler> dispatchers, UnexpectedRequestTypeDispatcher fallback) {
        this.fallback = fallback;
        this.registry = dispatchers.stream()
                .peek( h-> AnsiConsole.println("handler: " + h.getClass().getSimpleName(), AnsiConsole.BrightColor.CYAN))
                .collect(Collectors.toMap(
                        BasicActionHandler::key,
                        Function.identity(),
                        (a, b) -> {
                            throw new IllegalStateException(
                                    "Duplicate dispatcher for key: " + a.key() +
                                            " (" + a.getClass().getSimpleName() +
                                            " vs " + b.getClass().getSimpleName() + ")"
                            );
                        }
                ));


        registry.forEach((dispatchKey, basicActionHandler) -> System.out.println(dispatchKey+":"+basicActionHandler.getClass().getSimpleName()));
    }

    public void dispatch(RequestData requestData){
        registry.getOrDefault(new DispatchKey(requestData.getRole(), requestData.getType()), fallback)
                .dispatch(requestData);
    }


}
