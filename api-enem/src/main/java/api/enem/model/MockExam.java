package api.enem.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_mockexams")
public class MockExam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(nullable = false)
    private Integer totalQuestions;

    private Double score; // preenchido no final

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name = "mockexam_questions",
            joinColumns = @JoinColumn(name = "mockexam_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "mockExam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @PrePersist
    public void generateDefaultTitle() {
        if (this.title == null || this.title.isBlank()) {
            int total = totalQuestions != null ? totalQuestions : 0;
            this.title = "SIMULADO - "
                    + LocalDateTime.now().toLocalDate()
                    + " (" + total + " - questões)";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MockExam mockExam = (MockExam) o;
        return Objects.equals(id, mockExam.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}

