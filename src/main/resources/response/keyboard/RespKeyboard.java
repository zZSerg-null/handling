package response.keyboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.old.domain.enums.KeyboardType;

import java.util.List;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class RespKeyboard {

    private KeyboardType keyboardType;
    private List<ButtonRow> buttonRows;

    private boolean resizeKeyboard;
    private boolean removeKeyboard;
}
