package api.enem.repository;

import api.enem.model.MockExam;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface MockExamRepository extends Repository<MockExam, UUID> {
}