package ru.old.domain.dto.request.message;

import lombok.*;
import ru.old.domain.dto.request.FileType;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BasicFile {

    private FileType fileType;

    private String fileId;
    private String fileUniqueId;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Long fileSize;
    private String fileName;
    private String mimeType;
    private String type;
    private String emoji;
    private Boolean isAnimated;
    private Boolean isVideo;
    private String filePath;

    private Boolean hasMediaSpoiler;
}
