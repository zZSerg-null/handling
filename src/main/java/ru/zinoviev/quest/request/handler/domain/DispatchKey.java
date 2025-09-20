package ru.zinoviev.quest.request.handler.domain;

import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

import java.util.Objects;

public record DispatchKey(UserRole userRole, RequestType requestType){

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DispatchKey that = (DispatchKey) o;
        return userRole == that.userRole && requestType == that.requestType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userRole, requestType);
    }
}
