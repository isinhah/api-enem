package api.enem.repository;

import api.enem.model.Answer;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface AnswerRepository extends Repository<Answer, UUID> {
}