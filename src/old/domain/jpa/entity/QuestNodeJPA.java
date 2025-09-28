package ru.old.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.old.domain.enums.QuestNodeType;

import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QuestNodeJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Enumerated(EnumType.STRING)
    private QuestNodeType nodeType;

    @Column(columnDefinition = "TEXT")
    private String mainText;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    private QuestJPA questId;


    @ManyToOne
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "prev_node_id", nullable = true)
    private QuestNodeJPA prevNode;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "next_node_id", nullable = true)
    private QuestNodeJPA nextNode;


    @ElementCollection(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "expected_user_answers",
            joinColumns = @JoinColumn(name = "node_id"))
    private List<String> expectedUserAnswers;

    private int expectedAnswersCount;

    @ElementCollection(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "right_answers_react_messages",
            joinColumns = @JoinColumn(name = "node_id"))
    private List<String> correctAnswersReactMessages;

    @ElementCollection(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "wrong_answers_react_messages",
            joinColumns = @JoinColumn(name = "node_id"))
    private List<String> wrongAnswersReactMessages;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @Fetch(FetchMode.JOIN)
    private GeoPointJPA geoPoint;



    //poll
    @ElementCollection(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "poll_answers",
            joinColumns = @JoinColumn(name = "node_id"))
    @MapKeyColumn(name = "option_index")
    @Column(name = "option_value")
    private Map<Integer, String> pollAnswers;

    @ElementCollection(fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @CollectionTable(name = "correct_answers",
            joinColumns = @JoinColumn(name = "node_id"))
    private List<Integer> correctPollOptionId;

    private String explanation;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "node", orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<FileJPA> mediaList;

    private Boolean mainFieldIsEmpty;
}
