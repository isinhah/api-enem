package api.enem.repository;

import api.enem.model.Exam;
import api.enem.model.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionRepository extends JpaRepository<Question, UUID> {
    boolean existsByExamAndIndex(Exam exam, Integer index);
    Page<Question> findByDiscipline(String discipline, Pageable pageable);
    Page<Question> findByContextContainingIgnoreCase(String context, Pageable pageable);
}