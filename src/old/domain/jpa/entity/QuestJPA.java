package ru.old.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private QuestUserJPA author;

    @OneToMany
    private List<QuestNodeJPA> questNodeJPAList;

    @OneToOne(fetch = FetchType.LAZY)
    private QuestNodeJPA firstNode;

    private Boolean isComplete;
}
