package ru.zinoviev.quest.request.handler.domain;

import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;

public record DispatchKey(BotUserRole botUserRole, RequestType requestType){}
