package api.enem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_exams")
public class Exam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true, nullable = false, length = 100)
    private String title;

    @Column(nullable = false, name = "exam_year")
    private Integer year;

    @ElementCollection
    @CollectionTable(name = "tb_exam_disciplines", joinColumns = @JoinColumn(name = "exam_id"))
    private Set<ExamDiscipline> disciplines = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "tb_exam_languages", joinColumns = @JoinColumn(name = "exam_id"))
    private Set<ExamLanguage> languages = new HashSet<>();

    @OneToMany(mappedBy = "exam", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return Objects.equals(id, exam.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
