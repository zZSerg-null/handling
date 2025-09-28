package response.keyboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ButtonData {
    private String text;
    private String url;
    private String callbackData;
    private boolean primary;
    private boolean notEmpty;

    public ButtonData(String text, String callbackData) {
        this.text = text;
        this.callbackData = callbackData;
    }

}
