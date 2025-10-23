package api.enem.service;

import api.enem.model.enums.Role;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminService {

    private final UserService userService;

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        userService.findByEmail(dto.email());
        return userService.saveUserWithRole(dto, Role.ADMIN);
    }
}
