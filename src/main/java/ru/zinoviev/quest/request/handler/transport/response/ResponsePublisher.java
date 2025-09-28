package ru.zinoviev.quest.request.handler.transport.response;

import ru.zinoviev.quest.request.handler.domain.dto.response.ResponseData;

public interface ResponsePublisher {

    void sendResponse(ResponseData responseData);
}
