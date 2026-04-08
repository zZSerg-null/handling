package ru.zinoviev.quest.request.handler.domain.enums;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum RequestType {
    CALLBACK, LOCATION, MESSAGE, POLL, POLL_ANSWER, WEBAPP,

    @JsonEnumDefaultValue
    UNKNOWN
}
