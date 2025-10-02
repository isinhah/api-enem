package api.enem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class ExamLanguage {

    @Column(nullable = false, length = 150)
    private String label; // Ex: "Espanhol"

    @Column(name = "language_value", nullable = false, length = 50)
    private String value; // Ex: espanhol
}