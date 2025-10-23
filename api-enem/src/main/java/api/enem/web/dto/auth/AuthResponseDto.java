package api.enem.web.dto.auth;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.Instant;

public record AuthResponseDto(
        String token,
        String role,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Instant expiresAt
) {
}