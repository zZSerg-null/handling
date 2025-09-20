package ru.old.rest.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestPollAnswer {
    private String pollId;
    private List<Integer> optionIds;
}
