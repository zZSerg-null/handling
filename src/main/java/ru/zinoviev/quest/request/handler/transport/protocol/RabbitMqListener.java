package ru.zinoviev.quest.request.handler.transport.protocol;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.RequestAdapter;
import ru.zinoviev.quest.request.handler.transport.request.dto.TelegramRequest;

@Component
@RequiredArgsConstructor
public class RabbitMqListener {

    private final RequestAdapter adapter;

    @RabbitListener(queues = RabbitConfig.REQUEST_QUEUE)
    public void processRequest(TelegramRequest request) {
        AnsiConsole.println("⏬ получен запрос: " + request, AnsiConsole.BrightColor.BLUE);
        adapter.adaptAndProcessRequest(request);
    }


}
