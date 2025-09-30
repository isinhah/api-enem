package api.enem.repository;

import api.enem.model.MockExam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MockExamRepository extends JpaRepository<MockExam, UUID> {
}