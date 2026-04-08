package ru.zinoviev.quest.request.handler.domain.db.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zinoviev.quest.request.handler.domain.enums.BotUserRole;
import ru.zinoviev.quest.request.handler.domain.db.entity.BotUser;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<BotUser, Long> {

    @Query("select bu.botUserRole from BotUser bu where bu.telegramId = :telegramId")
    Optional<BotUserRole> getUserRoleByTelegramId(@Param("telegramId") Long telegramId);

    Optional<BotUser> findBotUserByTelegramId(Long telegramId);

}
