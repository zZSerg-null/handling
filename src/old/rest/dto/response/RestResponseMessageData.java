package ru.old.rest.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import old.rest.dto.response.keyboard.RestRespKeyboard;
import ru.old.domain.enums.PollType;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class RestResponseMessageData {

    private RespMessageType messageType;

    private Long chatId;
    private String text;
    private Integer messageId; // ID сообщения, которое будет изменено \ удалено
    private List<RestMessageEntityData> entity; // фишки текста сообщения, типа подчеркивания, спойлера и прочего
    private RestRespKeyboard replyMarkup; // клавиатурка
    private String parseMode;
    private boolean protectContent; // запрещена ли пересылка сообщений в другие чаты

    //опрос
    private PollType pollType;
    private String question;
    private List<String> options;
    private Integer correctOptionId;
    private Boolean allowMultipleAnswers; // если true то explanation и explanationEntities не работает
    private String explanation;
    private List<RestMessageEntityData> explanationEntities;

    private RestSendFile restSendFile; //если просто посылаем файл

    //Посылка группы файлов в одном сообщении
    private List<RestSendFile> mediaList;

    //Location
    private Double latitude;
    private Double longitude;
}
