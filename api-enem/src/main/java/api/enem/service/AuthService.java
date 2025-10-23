package api.enem.service;

import api.enem.model.User;
import api.enem.model.enums.Role;
import api.enem.repository.UserRepository;
import api.enem.security.JwtService;
import api.enem.web.dto.auth.AuthResponseDto;
import api.enem.web.dto.auth.LoginRequestDto;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponseDto login(LoginRequestDto dto) {
        User user = userRepository.findByEmail(dto.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        Instant expiresAt = jwtService.generateExpirationDate();

        return new AuthResponseDto(token, user.getRole().name(), expiresAt);
    }

    @Transactional
    public UserResponseDto register(UserRequestDto dto) {
        userService.findByEmail(dto.email());
        return userService.saveUserWithRole(dto, Role.USER);
    }
}