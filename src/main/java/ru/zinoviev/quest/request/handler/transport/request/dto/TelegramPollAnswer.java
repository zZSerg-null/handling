package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public final class TelegramPollAnswer extends TelegramRequest {

    private final String pollId;
    private final List<Integer> optionIds;

    @Builder
    public TelegramPollAnswer(Long userId, String userName,Integer messageId, String pollId, List<Integer> optionIds) {

        super(userId, userName, messageId);
        this.pollId = pollId;
        this.optionIds = optionIds;
    }
}
