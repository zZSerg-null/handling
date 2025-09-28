package ru.old.domain.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PollAnswer {
    private String pollId;
    private List<Integer> optionIds;
}
