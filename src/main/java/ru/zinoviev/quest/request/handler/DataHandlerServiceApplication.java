package ru.zinoviev.quest.request.handler;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class DataHandlerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataHandlerServiceApplication.class, args);
    }
}
