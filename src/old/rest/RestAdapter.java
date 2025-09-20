package ru.old.rest;

import lombok.RequiredArgsConstructor;
import ru.old.domain.RequestDispatcher;
import ru.old.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.response.QuestResponseData;
import ru.old.mapper.RestMapper;
import old.rest.dto.request.RestUpdateData;
import old.rest.dto.response.RestResponseData;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;

@Component
@RequiredArgsConstructor
public class RestAdapter {

    private final MyRestClient restClient;
    private final RequestDispatcher updateHandler;
    private final RestMapper mapper;
    private final LinkedBlockingQueue<RestUpdateData> requestQueue = new LinkedBlockingQueue<>();

    public RestResponseData getResponse(RestUpdateData updateData) {
        RequestData requestData = mapper.toServiceDto(updateData);

        QuestResponseData responseData = updateHandler.processUpdateData(requestData);
                return mapper.toRestResponse(responseData);
    }

    public void proceed(RestUpdateData updateData) {
        RequestData requestData = mapper.toServiceDto(updateData);
        QuestResponseData responseData = updateHandler.processUpdateData(requestData);
        restClient.sendResponse(mapper.toRestResponse(responseData));
    }

    public void proceedUpdate(RestUpdateData restUpdateData) {
        requestQueue.offer(restUpdateData);
    }
}
