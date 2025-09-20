package ru.old.domain.jpa.entity.last_response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ru.old.domain.enums.MessageEntityType;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntityDataJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private String text;

    @Enumerated(EnumType.STRING)
    private MessageEntityType entityType;
    private String type;
    private String url;  // .url("").type("text_link") используется только в такой связке
    private Integer offset_x;
    private Integer length_x;
}
