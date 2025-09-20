package ru.zinoviev.quest.request.handler.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.old.domain.enums.CreationStage;
import ru.old.domain.jpa.entity.QuestJPA;
import ru.old.domain.jpa.entity.QuestNodeJPA;
import ru.old.domain.jpa.entity.QuestUserJPA;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestCreationInfoJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private QuestUserJPA author;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private QuestJPA quest;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "creation_stages", joinColumns = @JoinColumn(name = "creation_info_id"))
    @Fetch(FetchMode.SELECT)
    private List<CreationStage> creationStatusList;

    @OneToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private QuestNodeJPA nodeJPA;
}
