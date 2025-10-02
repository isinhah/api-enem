package api.enem.repository;

import api.enem.model.QuestionAlternative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlternativeRepository extends JpaRepository<QuestionAlternative, UUID> {
}