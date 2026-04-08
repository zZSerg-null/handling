package ru.zinoviev.quest.request.handler.domain.db.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "bot_users")
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long telegramId;

    @Column(length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private BotUserRole botUserRole;

    @Column(length = 100)
    private String path;

}