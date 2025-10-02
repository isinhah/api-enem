package api.enem.model;

import api.enem.model.enums.AlternativeOption;
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

    @Lob
    @Column(nullable = false)
    private String context; // enunciado

    @ElementCollection
    @CollectionTable(name = "tb_question_files", joinColumns = @JoinColumn(name = "question_id"))
    private List<String> files = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private AlternativeOption correctAlternative;

    @Column(nullable = false)
    private String alternativesIntroduction;

    @Column(name = "discipline_value")
    private String discipline;

    @Column(name = "discipline_label", length = 150)
    private String disciplineLabel;

    @Column(name = "language_value")
    private String language;

    @Column(name = "language_label", length = 150)
    private String languageLabel;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<QuestionAlternative> alternatives = new ArrayList<>();

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
