package ru.old.domain.dto.request.message;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RequestOwner {
    private Long telegramUserId;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
}
