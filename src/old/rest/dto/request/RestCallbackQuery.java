package ru.old.rest.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestCallbackQuery {
    private String data;
    private Integer messageId;
}
