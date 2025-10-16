package ru.zinoviev.quest.request.handler.domain.jpa;

import jakarta.persistence.*;
import lombok.*;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long telegramId;

    @Column(length = 50)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Column(length = 100)
    private String path;

}