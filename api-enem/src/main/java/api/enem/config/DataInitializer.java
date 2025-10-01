package api.enem.config;

import api.enem.model.User;
import api.enem.model.enums.Role;
import api.enem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        if (userRepository.findByEmail("admin1@email.com").isEmpty()) {
            User admin = new User();
            admin.setName("admin1");
            admin.setEmail("admin1@gmail.com");
            admin.setPassword("123456");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}