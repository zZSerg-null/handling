package ru.old.domain.jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import old.domain.dto.request.FileType;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "nodeId")
    private QuestNodeJPA node;

    private FileType fileType;
    private String fileId;
    private String fileUniqueId;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Long fileSize;
    private String fileName;
    private String mimeType;
    private String fileExtensionType;
    private String emoji;
    private String name;
    private Boolean isAnimated;
    private Boolean isVideo;
    //PhotoInfo thumbnail

    private String fileUrl;
    private Boolean hasSpoiler;

}
