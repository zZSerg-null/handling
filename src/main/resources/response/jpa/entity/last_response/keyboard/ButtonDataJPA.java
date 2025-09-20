package ru.zinoviev.quest.request.handler.jpa.entity.last_response.keyboard;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ButtonDataJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private String text;
    private String url;
    private String callbackData;

    public ButtonDataJPA(String text) {
        this.text = text;
    }

    public ButtonDataJPA(String text, String callbackData) {
        this.text = text;
        this.callbackData = callbackData;
    }

    public ButtonDataJPA(String text, String url, String callbackData) {
        this.text = text;
        this.url = url;
        this.callbackData = callbackData;
    }
}
