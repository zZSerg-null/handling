package ru.zinoviev.quest.request.handler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ru.zinoviev.quest.request.handler.dto.TelegramLocation;
import ru.zinoviev.quest.request.handler.dto.TelegramMessage;
import ru.zinoviev.quest.request.handler.dto.TelegramPoll;
import ru.zinoviev.quest.request.handler.dto.TelegramRequest;

@Service
public class UpdateProcessor {

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void processUpdate(TelegramRequest request) {
        System.out.println("Got update: " + request);

        if (request instanceof TelegramMessage) {
            System.out.println(">>>>> MESSAGE");
        } else if (request instanceof TelegramPoll) {
            System.out.println(">>>>> POLL");
        } else if (request instanceof TelegramLocation) {
            System.out.println(">>>>> LOCATION");
        }
    }
}
