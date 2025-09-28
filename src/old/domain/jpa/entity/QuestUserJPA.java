package ru.old.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.old.domain.enums.UserRole;

import java.time.LocalDate;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class QuestUserJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long telegramId;

    private String nickname;

    /**
     * используется для определения роли пользователя для последующей обработки.
     * AUTHOR - роль по умолчанию. Может заниматься созданием квестов и управлением аккаунтом
     * PLAYER - роль, получаемая при входе в игру. Взаимодействует с запущенным квестом
     * ADMIN - мастер игры. Может как принимать участие в игре так и совершать управленческие действия в отношении
     *         как самого квеста так и игроков
     */
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    private LocalDate registrationDate;
}
