package ru.zinoviev.quest.request.handler.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.request.RequestDataMapper;
import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;
import ru.zinoviev.quest.request.handler.domain.jpa.service.UserRepositoryService;
import ru.zinoviev.quest.request.handler.transport.request.dto.TelegramRequest;

@Component
@RequiredArgsConstructor
public class RequestAdapter {

    private final UserRepositoryService service;
    private final RequestDataMapper mapper;
    private final RequestProcessor processor;

    public void adaptRequest(TelegramRequest telegramRequest) {
        System.out.println("RequestAdapter: " + telegramRequest);

        RequestData requestData = mapper.toRequestData(telegramRequest);
        UserInfo info = service.getOrCreateUserInfo(
                requestData.getTelegramId(),
                requestData.getUserName()
        );
        requestData.setPath(info.getPath());
        processor.process(info.getRole(), requestData.getType(), requestData);
    }


}
