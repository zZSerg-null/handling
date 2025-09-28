package ru.zinoviev.quest.request.handler.transport.request.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public final class TelegramPoll extends TelegramRequest {

    private final String pollId;
    private final String question;
    private final String explanation;
    private final PollType type;
    private final Boolean allowMultipleAnswers;
    private final List<String> answers;
    private final List<Integer> correctAnswersId;

    @Builder
    public TelegramPoll(Long userId, String userName, Integer messageId, String pollId, String question,
                        String explanation, PollType type, Boolean allowMultipleAnswers, List<String> answers,
                        List<Integer> correctAnswersId) {
        super(userId, userName, messageId);

        this.type = type;
        this.allowMultipleAnswers = allowMultipleAnswers;

        this.explanation = explanation;
        this.pollId = pollId;
        this.question = question;
        this.answers = answers;
        this.correctAnswersId = correctAnswersId;
    }


}

