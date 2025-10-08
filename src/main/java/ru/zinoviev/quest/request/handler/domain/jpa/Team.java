package ru.zinoviev.quest.request.handler.domain.jpa;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

/**
 * Класс,
 */

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Team {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * сам квест, в котором участвует команда
     */
    @OneToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    /**
     * текущее задание
     */
    @OneToOne
    @JoinColumn(name = "quest_node_id")
    private QuestNode currentQuestNode;

    /**
     * участники
     */
    @OneToMany
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "team_members",
            joinColumns = @JoinColumn(name = "team_id"),
            inverseJoinColumns = @JoinColumn(name = "bot_user_id")
    )
    private List<BotUser> members;

    /**
     * Открыт ли квест для регистрации новых участников
     */
    private Boolean isPrivate;
}
