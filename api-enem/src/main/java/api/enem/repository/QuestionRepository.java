package api.enem.repository;

import api.enem.model.Question;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface QuestionRepository extends Repository<Question, UUID> {
}