package api.enem.repository;

import api.enem.model.Alternative;
import org.springframework.data.repository.Repository;

import java.util.UUID;

public interface AlternativeRepository extends Repository<Alternative, UUID> {
}