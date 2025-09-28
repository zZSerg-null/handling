package ru.old.domain.jpa.service;

import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;
import old.domain.dto.response.RespMessageData;
import ru.old.domain.enums.UserRole;

import java.util.List;

public interface LastResponseJPARepositoryService {
    List<RespMessageData> getLastServiceResponseToUser(RequestData request, UserRole userRole);

    void saveLastServiceResponseToUser(QuestResponseData responseData);
}
