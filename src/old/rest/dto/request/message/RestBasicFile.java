package ru.old.rest.dto.request.message;

import lombok.*;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RestBasicFile {

    private RestFileType fileType;

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
    private String setName;
    private Boolean isAnimated;
    private Boolean isVideo;
    private String filePath;

    private Boolean hasMediaSpoiler;
}
