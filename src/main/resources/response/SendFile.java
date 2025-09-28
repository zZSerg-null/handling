package response;

import lombok.Builder;
import lombok.Getter;
import ru.old.domain.enums.SendFileType;

import java.util.List;

@Getter
@Builder
public class SendFile {
    private String caption;
    private List<MessageEntityData> captionEntities;
    private SendFileType sendFileType;
    private String fileUrl; // InputFile("")
    private boolean hasSpoiler;
}
