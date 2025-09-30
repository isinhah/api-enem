package api.enem.model;

import api.enem.model.enums.Language;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

@Embeddable
public class ExamLanguage {

    @Column(nullable = false, length = 150)
    private String label; // Ex: "Espanhol"

    @Enumerated(EnumType.STRING)
    @Column(name = "language_value", nullable = false, length = 50)
    private Language value; // Ex: espanhol
}