package ru.zinoviev.quest.request.handler.domain.jpa.service;

import ru.zinoviev.quest.request.handler.domain.dto.response.PayloadResponse;

public interface PayloadService {

    PayloadResponse getPayload(String name);

}
