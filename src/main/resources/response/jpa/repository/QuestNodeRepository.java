package ru.zinoviev.quest.request.handler.jpa.repository;

import old.domain.jpa.entity.QuestNodeJPA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestNodeRepository extends JpaRepository<QuestNodeJPA, Long> {

    @Query("""
            select qn 
            from QuestNodeJPA qn
            where qn.questId.id = ?1
                """)
    List<QuestNodeJPA> findByQuestId(long questId);

    @Query("""
              select COUNT(eua) > 0 
              from QuestNodeJPA qn 
              join qn.expectedUserAnswers eua
              where qn.id = :nodeId
            """
    )
    boolean isNodeExpectedAnswersPresent(Long nodeId);

    @Query("""
              select COUNT(carm)  > 0 
              from QuestNodeJPA qn
              join qn.correctAnswersReactMessages carm
              where qn.id =:id
            """)
    boolean isNodeCorrectAnswersReactMessagesPresent(Long id);

    @Query("""
              select COUNT(warm)  > 0 
              from QuestNodeJPA qn
              join qn.wrongAnswersReactMessages warm
              where qn.id =:id
            """)
    boolean isNodeWrongAnswersReactMessagesPresent(Long id);

    @Query("""
              select COUNT(pa)  > 0 
              from QuestNodeJPA qn
              join qn.pollAnswers pa
              where qn.id =:id
            """)
    boolean isNodePollAnswersPresent(Long id);

    @Query("""
              select COUNT(cpo)  > 0 
              from QuestNodeJPA qn
              join qn.correctPollOptionId cpo
              where qn.id =:id
            """)
    boolean isNodeCorrectPollOptionPresent(Long id);

    @Query("""
              select COUNT(ml)  > 0 
              from QuestNodeJPA qn
              join qn.mediaList ml
              where qn.id =:id
            """)
    boolean isNodeMedialistPresent(Long id);
}
