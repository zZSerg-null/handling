package ru.old.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import old.domain.dto.request.FileType;

@Getter
@Setter
@Builder
@ToString
public class BasicFileInfo {
    private FileType fileType;
    private Long fileSize;
    private String fileName;
    private String mimeType;
}
