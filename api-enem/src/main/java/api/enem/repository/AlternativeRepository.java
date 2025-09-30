package api.enem.repository;

import api.enem.model.Alternative;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AlternativeRepository extends JpaRepository<Alternative, UUID> {
}