package ru.zinoviev.quest.request.handler.jpa.repository;

import old.domain.jpa.entity.QuestCreationInfoJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface QuestCreationRepository extends JpaRepository<QuestCreationInfoJPA, Long> {

    Optional<QuestCreationInfoJPA> findByAuthor_Id(Long id);

    @Query("""
                select ci from QuestCreationInfoJPA ci
                join ci.creationStatusList csl
                where ci.id =?1       
            """)
    QuestCreationInfoJPA getStatusList(Long creationId);

//    @Query("""
//            select qc from QuestCreationInfoJPA qc
//            join fetch qc.author
//            join fetch qc.nodeJPA
//            join fetch qc.quest
//            where qc.author.id = ?1
//            """)
//    Optional<QuestCreationInfoJPA> findByUserId(Long id);
}
