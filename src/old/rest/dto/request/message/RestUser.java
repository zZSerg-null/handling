package ru.old.rest.dto.request.message;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestUser {
    private Long userId;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
}
