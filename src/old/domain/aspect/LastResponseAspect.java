package ru.old.domain.aspect;

import lombok.RequiredArgsConstructor;
import old.domain.dto.response.QuestResponseData;
import org.aspectj.lang.annotation.AfterReturning;
import old.domain.dto.response.RespMessageData;
import old.domain.enums.RetryType;
import old.domain.jpa.service.LastResponseJPARepositoryService;

//@Aspect
//@Component
@RequiredArgsConstructor
public class LastResponseAspect {

    private final LastResponseJPARepositoryService lastResponseJPARepositoryService;

    @AfterReturning(pointcut = "execution(* ru.quest_bot.data_handler_service.domain.RequestDispatcher.processUpdateData(..))", returning = "responseData")
    public void saveResponseData(QuestResponseData responseData) {
        if (responseData != null) {
            var responseMessageList = responseData.getMessageDataList();
            boolean saveNeeded = false;
            for (RespMessageData messageData : responseMessageList) {
                if (messageData.getRetryType().equals(RetryType.RETRYABLE)) {
                    saveNeeded = true;
                    break;
                }
            }

            if (saveNeeded) {
                lastResponseJPARepositoryService.saveLastServiceResponseToUser(responseData);
            }
        }
    }

}
