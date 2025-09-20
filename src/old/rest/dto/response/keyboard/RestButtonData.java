package ru.old.rest.dto.response.keyboard;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
public class RestButtonData {
    private String text;
    private String url;
    private String callbackData;

    public RestButtonData(String text) {
        this.text = text;
    }

    public RestButtonData(String text, String callbackData) {
        this.text = text;
        this.callbackData = callbackData;
    }

    public RestButtonData(String text, String url, String callbackData) {
        this.text = text;
        this.url = url;
        this.callbackData = callbackData;
    }
}
