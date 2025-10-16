package ru.zinoviev.quest.request.handler.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.action.ActionDispatcher;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestDataMapper;
import ru.zinoviev.quest.request.handler.domain.jpa.UserInfo;
import ru.zinoviev.quest.request.handler.domain.jpa.service.UserRepositoryService;
import ru.zinoviev.quest.request.handler.transport.request.dto.TelegramRequest;


/**
 * Класс предназначенный для маппинга входящих запросов, поиска информации в базе данных
 * и передаче внутреннего ДТО непосредственно на обработку
 */
@Component
@RequiredArgsConstructor
public class RequestAdapter {

    private final UserRepositoryService service;
    private final RequestDataMapper mapper;
    private final DispatcherRegistry registry;

    public void adaptAndProcessRequest(TelegramRequest telegramRequest) {
        UserInfo info = service.getOrCreateUserInfo(
                telegramRequest.getUserId(), telegramRequest.getUserName()
        );

        RequestData requestData = mapper.toRequestData(telegramRequest);
        requestData.setPath(info.getPath());
        requestData.setRole(info.getRole());
        requestData.setUserId(info.getQuestUserId());

        registry.get(info.getRole(), requestData.getType())
                .dispatch(requestData);
    }


}
