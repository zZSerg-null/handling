package ru.old.domain.dto.request;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CallbackData {
    private String data;
    private Integer messageId;
}
