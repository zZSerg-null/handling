package ru.zinoviev.quest.request.handler.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestData;
import ru.zinoviev.quest.request.handler.domain.dto.internal.RequestDataMapper;
import ru.zinoviev.quest.request.handler.domain.dto.internal.UserInfo;
import ru.zinoviev.quest.request.handler.domain.db.service.UserRepositoryService;
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

    public void adaptAndDispatchRequest(TelegramRequest telegramRequest) {
        UserInfo userInfo = service
                .getUserInfo(telegramRequest.getTelegramId(), telegramRequest.getUserName())
                .orElseGet(()->service.createUser(telegramRequest.getTelegramId(), telegramRequest.getUserName()));

        if (!userInfo.getUserName().equals(telegramRequest.getUserName())){
            service.updateUser(telegramRequest.getTelegramId(), telegramRequest.getUserName());
        }

        RequestData requestData = mapper.toRequestData(telegramRequest);
        requestData.setRole(userInfo.getRole());
        requestData.setUserId(userInfo.getUserId());

        registry.dispatch(requestData);
    }


}
