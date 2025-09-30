package api.enem.model;

import api.enem.model.enums.Discipline;
import api.enem.model.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_questions")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 1)
    private Integer index;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String context; // enunciado

    @ElementCollection
    @CollectionTable(name = "tb_question_files", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> files = new ArrayList<>();

    @Column(nullable = false, length = 1)
    private String correctAlternative;

    @Column(nullable = false)
    private String alternativesIntroduction;

    @Enumerated(EnumType.STRING)
    @Column(name = "discipline_value", nullable = false)
    private Discipline discipline;

    @Column(name = "discipline_label", nullable = false, length = 150)
    private String disciplineLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "language_value", nullable = false)
    private Language language;

    @Column(name = "language_label", nullable = false, length = 150)
    private String languageLabel;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Alternative> alternatives = new ArrayList<>();

    @ManyToOne(optional = false)
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(id, question.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
