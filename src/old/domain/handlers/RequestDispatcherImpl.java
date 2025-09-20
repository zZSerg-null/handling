package ru.old.domain.handlers;

import lombok.RequiredArgsConstructor;
import ru.old.domain.RequestDispatcher;
import old.domain.handlers.h1.BasicHandler;
import org.springframework.stereotype.Service;
import old.domain.dto.UserInfo;
import old.domain.dto.request.RequestData;
import old.domain.dto.request.message.RequestOwner;
import old.domain.dto.response.QuestResponseData;
import old.domain.enums.RequestMessageType;
import old.domain.enums.UserRole;
import old.domain.jpa.service.UserRepositoryService;
import ru.old.domain.dto.UserInfo;
import ru.old.domain.dto.request.RequestData;
import ru.zinoviev.quest.request.handler.response.QuestResponseData;
import ru.old.domain.enums.RequestMessageType;
import ru.old.domain.enums.UserRole;
import ru.old.domain.handlers.roles.impl.BasicHandler;
import ru.old.domain.jpa.service.UserRepositoryService;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RequestDispatcherImpl implements RequestDispatcher {
    private final UserRepositoryService userRepositoryService;

    /**
     * содержит реализации обработчиков запроса пользователя по ролям (статусу)
     */
    private final Map<UserRole, Map<RequestMessageType, BasicHandler>> handlers;


    /**
     * Получение информации о пользователе с последующей отдачей на обработку
     *
     * @param request - запрос пользователя
     * @return - ответ сервиса
     */
    @Override
    public QuestResponseData processUpdateData(RequestData request) {
        RequestOwner user = request.getFrom();
        String actualUserName = !user.getFirstName().isBlank() ? user.getFirstName() : user.getUserName();

        UserInfo userInfo = userRepositoryService.getUserInfo(user.getTelegramUserId(), actualUserName);

        return processByUserRole(userInfo, request);
    }


    /**
     * Метод обработки сообщений в зависимости от роли юзера и типа сообщения:
     *
     * USER.Role | Message.Type | UserMessageHandler.handler
     * USER.Role | Callback.Type | UserCallbackHandler.handler
     * ADMIN.Role | Callback.Type | AdminCallbackHandler.handler
     * ...
     *
     * @param request - входящий запрос
     * @param userInfo - данные о пользователе полученные из базы
     * @return - готовый ответ сервиса для возврата пользователю
     */
    private QuestResponseData processByUserRole(UserInfo userInfo, RequestData request) {
        return handlers //TODO сделать проверку на налы и прочее, вдруг что то из гетов будет нал?
                .get(userInfo.getStatus()) // status
                .get(request.getMessageType()) //message type: message, callback, pollAnswer..
                .handle(request, userInfo); // concrete handler
    }




 /*
    private final Map<UserRole, RoleHandler> userRoleHandlerMap

    @Override
    public QuestResponseData processUpdateData(RequestData request) {
        UserInfo userInfo = getUserInfo(request);
        return processByUserRole(userInfo, request);
    }

    private UserInfo getUserInfo(RequestData request) {
        RequestOwner user = request.getFrom();
        String actualUserName = !user.getFirstName().isBlank() ? user.getFirstName() : user.getUserName();
        return userRepositoryService.getUserInfo(user.getTelegramUserId(), actualUserName);
    }

    private QuestResponseData processByUserRole(UserInfo userInfo, RequestData request) {
        return userRoleHandlerMap.get(userInfo.getStatus()).getResponse(request, userInfo);
    }
*/



}
