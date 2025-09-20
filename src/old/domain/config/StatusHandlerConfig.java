package ru.old.domain.config;

import ru.old.domain.handlers.h1.BasicHandler;
import ru.old.domain.handlers.h1.BasicMessageBuilder;
import ru.old.domain.handlers.h1.UnknownMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import old.domain.enums.RequestMessageType;
import ru.old.domain.handlers.h1.creator.CreatorCallbackHandler;
import ru.old.domain.handlers.h1.creator.CreatorMessageHandler;
import ru.old.domain.handlers.h1.user.UserCallbackHandler;
import ru.old.domain.handlers.h1.user.UserMessageBuilder;
import ru.old.domain.handlers.h1.user.UserMessageHandler;
import ru.old.domain.handlers.roles.AdminRoleHandler;
import ru.old.domain.handlers.roles.CreatorRoleHandler;
import ru.old.domain.handlers.roles.PlayerRoleHandler;
import ru.old.domain.handlers.roles.RoleHandler;
import old.domain.enums.UserRole;
import ru.old.domain.handlers.roles.UserRoleHandler;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class StatusHandlerConfig {
    @Bean
    public Map<UserRole, RoleHandler> statusHandlerMap(CreatorRoleHandler creatorStatusHandler,
                                                       PlayerRoleHandler playerStatusHandler,
                                                       AdminRoleHandler adminStatusHandler,
                                                       UserRoleHandler UserRoleHandler) {

        Map<UserRole, RoleHandler> statusHandlerMap = new HashMap<>();

        statusHandlerMap.put(UserRole.CREATOR, creatorStatusHandler);
        statusHandlerMap.put(UserRole.PLAYER, playerStatusHandler);
        statusHandlerMap.put(UserRole.ADMIN, adminStatusHandler);
        statusHandlerMap.put(UserRole.USER, UserRoleHandler);

        return statusHandlerMap;
    }



    @Bean
    public Map<UserRole, Map<RequestMessageType, BasicHandler>> handlersMap(UnknownMessageHandler unknownMessageHandler,
                                                                            UserMessageBuilder userMessageBuilder
                                                                            ) {
        Map<UserRole, Map<RequestMessageType, BasicHandler>> statusHandlerMap = new HashMap<>();

       // statusHandlerMap.put(UserRole.CREATOR, getCreatorHandlersMap(unknownMessageHandler, responseMessageBuilder));
       // statusHandlerMap.put(UserRole.PLAYER, getPlayerHandlersMap(unknownMessageHandler, responseMessageBuilder));
       // statusHandlerMap.put(UserRole.ADMIN, getAdminHandlersMap(unknownMessageHandler, responseMessageBuilder));
        statusHandlerMap.put(UserRole.USER, getUserHandlersMap(unknownMessageHandler, userMessageBuilder));

        return statusHandlerMap;
    }


    private Map<RequestMessageType, BasicHandler> getUserHandlersMap(UnknownMessageHandler umh,
                                                                     UserMessageBuilder userMessaheBuilder){
        Map<RequestMessageType, BasicHandler> userHandler = new HashMap<>();

        userHandler.put(RequestMessageType.MESSAGE, new UserMessageHandler(userMessaheBuilder));
        userHandler.put(RequestMessageType.CALLBACK, new UserCallbackHandler(userMessaheBuilder));
        userHandler.put(RequestMessageType.POLL_ANSWER, umh);
        userHandler.put(RequestMessageType.UNKNOWN, umh);
        return userHandler;
    }

    private Map<RequestMessageType, BasicHandler> getAdminHandlersMap(UnknownMessageHandler umh,
                                                                      BasicMessageBuilder rmb){
        Map<RequestMessageType, BasicHandler> adminHandler = new HashMap<>();

        adminHandler.put(RequestMessageType.MESSAGE, umh);
        adminHandler.put(RequestMessageType.CALLBACK, umh);
        adminHandler.put(RequestMessageType.POLL_ANSWER, umh);
        adminHandler.put(RequestMessageType.UNKNOWN, umh);
        return adminHandler;
    }

    private Map<RequestMessageType, BasicHandler> getCreatorHandlersMap(UnknownMessageHandler umh,
                                                                        BasicMessageBuilder rmb){
        Map<RequestMessageType, BasicHandler> creatorHandler = new HashMap<>();

        creatorHandler.put(RequestMessageType.MESSAGE, new CreatorMessageHandler());
        creatorHandler.put(RequestMessageType.CALLBACK, new CreatorCallbackHandler());
        creatorHandler.put(RequestMessageType.POLL_ANSWER, umh);
        creatorHandler.put(RequestMessageType.UNKNOWN, umh);
        return creatorHandler;
    }

    private Map<RequestMessageType, BasicHandler> getPlayerHandlersMap(UnknownMessageHandler umh,
                                                                       BasicMessageBuilder rmb){
        Map<RequestMessageType, BasicHandler> playerHandler = new HashMap<>();

        playerHandler.put(RequestMessageType.MESSAGE, umh);
        playerHandler.put(RequestMessageType.CALLBACK, umh);
        playerHandler.put(RequestMessageType.POLL_ANSWER, umh);
        playerHandler.put(RequestMessageType.UNKNOWN, umh);
        return playerHandler;
    }

}
