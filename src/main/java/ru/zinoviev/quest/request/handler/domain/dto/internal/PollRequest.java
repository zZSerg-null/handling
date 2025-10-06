package ru.zinoviev.quest.request.handler.domain.dto.internal;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.domain.enums.RequestType;
import ru.zinoviev.quest.request.handler.transport.request.dto.PollType;

import java.util.List;

@Getter
@ToString
public final class PollRequest extends RequestData {

    private final String pollId;
    private final String question;
    private final String explanation;
    private final PollType type;
    private final Boolean allowMultipleAnswers;
    private final List<String> answers;
    private final List<Integer> correctAnswersId;


    @Builder
    public PollRequest(Long telegramId, String userName, Integer messageId, String pollId, String question, String explanation, PollType type, Boolean allowMultipleAnswers, List<String> answers, List<Integer> correctAnswersId) {
        super(telegramId, userName, messageId);
        this.pollId = pollId;
        this.question = question;
        this.explanation = explanation;
        this.type = type;
        this.allowMultipleAnswers = allowMultipleAnswers;
        this.answers = answers;
        this.correctAnswersId = correctAnswersId;
    }

    @Override
    public RequestType getType() {
        return RequestType.POLL;
    }
}
