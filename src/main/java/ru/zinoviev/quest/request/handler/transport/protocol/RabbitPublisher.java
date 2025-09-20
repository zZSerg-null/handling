package ru.zinoviev.quest.request.handler.transport.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;
import ru.zinoviev.quest.request.handler.transport.response.ResponsePublisher;

@Component
public class RabbitPublisher implements ResponsePublisher {

    public final RabbitTemplate template;
    private static final Logger LOG = LoggerFactory.getLogger("RabbitPublisher");

    public RabbitPublisher(RabbitTemplate template) {
        this.template = template;

        template.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("✅ Сообщение подтверждено брокером");
            } else {
                System.err.println("❌ Сообщение не дошло: " + cause);
                LOG.warn("Сообщение возвращено: {}", correlationData);
                retrySend(correlationData, cause);
            }
        });

        template.setReturnsCallback(returned -> {
            LOG.warn("Сообщение возвращено: {}", returned.getMessage());
            LOG.warn("Причина: exchange={}, routingKey={}, replyCode={}",
                    returned.getExchange(), returned.getRoutingKey(), returned.getReplyCode());
            // Обработка возвращенного сообщения
        });

        template.setRecoveryCallback(recovery -> {
            LOG.error("Ошибка при работе с RabbitMQ: {}", recovery.getLastThrowable());
            // Решение о повторной попытке или альтернативной обработке
            return null; // или альтернативный результат
        });
    }

    @Override
    public void sendResponse(ResponseData responseData) {
        AnsiConsole.println("⏫ Отправляем ответ: " + responseData, AnsiConsole.BrightColor.GREEN);

        template.convertAndSend(
                RabbitConfig.RESPONSE_QUEUE,
                responseData,
                message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    return message;
                }
        );
    }

    private void retrySend(CorrelationData correlationData, String cause) {
        for (int i = 1; i <= 3; i++) {
            try {
                Thread.sleep((long) Math.pow(2, i) * 1000);
                template.convertAndSend(
                        RabbitConfig.RESPONSE_QUEUE,
                        correlationData != null ? correlationData.getId() : "retry-message"
                );
                return;
            } catch (Exception e) {
                System.err.println("Retry " + i + " failed: " + e.getMessage());
            }
        }
        // после 3 попыток можно залогировать в БД или в отдельный retry-лог
    }


}
