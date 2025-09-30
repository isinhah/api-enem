package api.enem.repository;

import api.enem.model.Exam;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface ExamRepository extends Repository<Exam, UUID> {
}