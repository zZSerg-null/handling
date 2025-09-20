package ru.zinoviev.quest.request.handler.domain.jpa;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long telegramId;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private String path;

}