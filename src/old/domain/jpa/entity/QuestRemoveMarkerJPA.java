package ru.old.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestRemoveMarkerJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate deleteDate;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private QuestJPA questJPA;
}
