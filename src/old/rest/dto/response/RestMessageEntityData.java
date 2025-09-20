package ru.old.rest.dto.response;

import lombok.Builder;
import lombok.Getter;
import ru.old.domain.enums.MessageEntityType;

@Getter
@Builder
public class RestMessageEntityData {
    private String text;
    private MessageEntityType entityType;
    private String url;  // .url("").type("text_link") используется только в такой связке
    private Integer offset;
    private Integer length;
}
