package api.enem.web.dto.user;

import api.enem.model.enums.Role;

public record UserResponseDto(
        Long id,
        String name,
        String email,
        Role role
) {
}