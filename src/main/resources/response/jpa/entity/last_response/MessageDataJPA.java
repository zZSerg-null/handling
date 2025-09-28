package ru.zinoviev.quest.request.handler.jpa.entity.last_response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import old.domain.jpa.entity.last_response.keyboard.RespKeyboardJPA;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.old.domain.enums.PollType;
import ru.old.domain.jpa.entity.last_response.MessageEntityDataJPA;
import ru.old.domain.jpa.entity.last_response.SendFileJPA;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDataJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @Enumerated(EnumType.STRING)
    private RespMessageType messageType;
    private Long chatId;

    @Column(columnDefinition = "TEXT")
    private String text;
    private Integer messageId; // ID сообщения, которое будет изменено \ удалено

    private String parseMode;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<ru.old.domain.jpa.entity.last_response.MessageEntityDataJPA> entity; // фишки текста сообщения, типа подчеркивания, спойлера и прочего

    @OneToOne(cascade = CascadeType.ALL)
    private RespKeyboardJPA replyMarkup; // клавиатурка
    private boolean protectContent; // запрещена ли пересылка сообщений в другие чаты

    //опрос
    @Enumerated(EnumType.STRING)
    private PollType pollType;

    @Column(columnDefinition = "TEXT")
    private String question;

    @ElementCollection
    @CollectionTable(name = "options",
            joinColumns = @JoinColumn(name = "resonse_id"))
    private List<String> options;

    private Integer correctOptionId;
    private Boolean allowMultipleAnswers;

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<MessageEntityDataJPA> explanationEntities;

    @OneToOne(cascade = CascadeType.ALL)
    private ru.old.domain.jpa.entity.last_response.SendFileJPA sendFileJPA; //если просто посылаем файл

    //Посылка группы файлов в одном сообщении
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<SendFileJPA> mediaList;

    //Location
    private Double latitude;
    private Double longitude;


}
