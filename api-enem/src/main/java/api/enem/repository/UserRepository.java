package api.enem.repository;

import api.enem.model.User;
import org.springframework.data.repository.Repository;

public interface UserRepository extends Repository<User, Long> {
}