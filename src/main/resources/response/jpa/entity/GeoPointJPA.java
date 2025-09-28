package ru.zinoviev.quest.request.handler.jpa.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GeoPointJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private Double longitude;
    private Double latitude;
    private Integer geoActiveRadius; // min - 15

    @Column(columnDefinition = "TEXT")
    private String radiusMessage;
}
