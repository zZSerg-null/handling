package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;

import java.util.List;

@Getter
@ToString
public final class PollAnswerRequest extends RequestData {

    private final String pollId;
    private final List<Integer> optionIds;

    @Builder
    public PollAnswerRequest(Long telegramId, String userName, Integer messageId, String pollId, List<Integer> optionIds) {
        super(telegramId, userName, messageId);
        this.pollId = pollId;
        this.optionIds = optionIds;
    }

    @Override
    public RequestType getType() {
        return RequestType.POLL_ANSWER;
    }
}
