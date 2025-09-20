package response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.old.domain.enums.PollType;
import ru.zinoviev.quest.request.handler.response.keyboard.RespKeyboard;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class QuestResponseData {

    private RespMessageType messageType;

    private Long chatId;
    private String text;
    private Integer messageId; // ID сообщения, которое будет изменено \ удалено
    private String parseMode;
    private RespKeyboard replyMarkup; // клавиатурка
    private boolean protectContent; // запрещена ли пересылка сообщений в другие чаты

    //опрос
    private PollType pollType;
    private String question;
    private List<String> options;
    private Integer correctOptionId;
    private Boolean allowMultipleAnswers; // если true то explanation и explanationEntities не работает
    private String explanation;
    private List<MessageEntityData> explanationEntities;

    //Посылка группы файлов в одном сообщении
    private List<SendFile> mediaList;

    //Location
    private Double latitude;
    private Double longitude;
}
