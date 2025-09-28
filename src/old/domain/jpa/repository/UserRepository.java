package ru.old.domain.jpa.repository;

public interface UserRepository extends JpaRepository<QuestUserJPA, Long> {

    @Query(value = "select u from QuestUserJPA u where u.telegramId = ?1")
    Optional<QuestUserJPA> findByTelegramId(Long userId);
}
