package ru.zinoviev.quest.request.handler.domain.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import ru.zinoviev.quest.request.handler.transport.request.dto.PayloadType;

@Getter
@Builder
@ToString
public class PayloadResponse {

    private PayloadType payloadType;
    private String caption;

    private String fileId;
    private String fileUniqueId;
    private Integer width;
    private Integer height;
    private Integer duration;
    private Long fileSize;
    private String fileName;
    private String mimeType;
    private Boolean isAnimated;
    private String filePath;
}
