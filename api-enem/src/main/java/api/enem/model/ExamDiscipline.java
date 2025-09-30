package api.enem.model;

import api.enem.model.enums.Discipline;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ExamDiscipline {

    @Column(nullable = false, length = 150)
    private String label; // Ex: "Ciências Humanas e suas Tecnologias"

    @Enumerated(EnumType.STRING)
    @Column(name = "discipline_value", nullable = false, length = 50)
    private Discipline value; // Ex: HUMANAS
}
