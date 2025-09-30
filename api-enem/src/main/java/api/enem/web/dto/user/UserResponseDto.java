package api.enem.web.dto.user;

import api.enem.model.enums.Role;

import java.util.UUID;

public record UserResponseDto(
        UUID id,
        String name,
        String email,
        Role role
) {
}