package ru.zinoviev.quest.request.handler.domain.action;

public class UnhandableRequestTypeException extends RuntimeException {

    public UnhandableRequestTypeException(String message) {
        super(message);
    }
}
