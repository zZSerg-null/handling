package ru.zinoviev.quest.request.handler.domain.jpa.repo;

import org.hibernate.annotations.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zinoviev.quest.request.handler.domain.enums.UserRole;
import ru.zinoviev.quest.request.handler.domain.jpa.BotUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<BotUser, Long> {

    @Query("select bu.userRole from BotUser bu where bu.telegramId = :telegramId")
    Optional<UserRole> getUserRoleByTelegramId(@Param("telegramId") Long telegramId);

    Optional<BotUser> findBotUserByTelegramId(Long telegramId);

}
