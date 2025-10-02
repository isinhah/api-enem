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
public class ExamDiscipline {

    @Column(nullable = false, length = 150)
    private String label; // Ex: Ciências Humanas e suas Tecnologias

    @Column(name = "discipline_value", nullable = false, length = 50)
    private String value; // Ex: ciencias-humanas
}
