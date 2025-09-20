package ru.zinoviev.quest.request.handler.jpa.entity.last_response.keyboard;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import ru.old.domain.enums.KeyboardType;
import ru.old.domain.jpa.entity.last_response.keyboard.ButtonRowJPA;

import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RespKeyboardJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long Id;

    private KeyboardType keyboardType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    private List<ButtonRowJPA> buttonRowJPAS;

    private boolean resizeKeyboard;
    private boolean removeKeyboard;
}
