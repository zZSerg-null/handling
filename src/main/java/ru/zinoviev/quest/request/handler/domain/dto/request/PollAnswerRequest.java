package ru.zinoviev.quest.request.handler.domain.dto.request;

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
    public PollAnswerRequest(Long telegramId, String userName, String pollId, List<Integer> optionIds) {
        super(telegramId, userName);
        this.pollId = pollId;
        this.optionIds = optionIds;
    }

    @Override
    public RequestType getType() {
        return RequestType.POLL_ANSWER;
    }
}
