package api.enem.web.dto.user;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequestDto(
        @NotBlank(message = "Current password cannot be empty") String currentPassword,
        @NotBlank(message = "New password cannot be empty") String newPassword)
{}
