package ru.old.rest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

@Configuration
public class AsyncConfig {

    @Bean
    public UpdateQueueWorkerThread updateQueueWorkerThread(@Lazy RestAdapter adapter){
        return new UpdateQueueWorkerThread(adapter);
    }
}
