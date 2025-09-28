package ru.old.domain.jpa.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

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
