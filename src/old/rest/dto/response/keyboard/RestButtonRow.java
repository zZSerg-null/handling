package ru.old.rest.dto.response.keyboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestButtonRow {
    private List<RestButtonData> buttons;
}
