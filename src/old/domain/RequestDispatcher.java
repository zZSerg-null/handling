package ru.old.domain;

import old.domain.dto.request.RequestData;
import old.domain.dto.response.QuestResponseData;

/**
 * Интерфейс для взаимодейстия с сервисом квеста
 */
public interface RequestDispatcher {
    /**
     * Единственный метод принимает на вход ДТО сервиса, которую необходимо получить из транспорта, реализовав
     * собсвенный маппер, и возвращает ответ сервиса, который так же надо смаппить в транспорт для отправки
     *
     * @param request - входящие данные
     * @return - сущность сервисного слоя представляющая собой результат обработки запроса.
     */

    QuestResponseData processUpdateData(RequestData request);
}
