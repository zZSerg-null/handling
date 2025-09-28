package ru.old.rest.dto.response;

import lombok.Builder;
import lombok.Getter;
import ru.old.domain.enums.SendFileType;

import java.util.List;

@Getter
@Builder
public class RestSendFile {
    private String caption;
    private List<RestMessageEntityData> captionEntities;
    private SendFileType sendFileType;
    private String fileUrl; // InputFile("")
    private boolean hasSpoiler;
}
