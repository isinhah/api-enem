package api.enem.web.controller;

import api.enem.service.UserService;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/admins")
    public UserResponseDto createAdmin(@Valid @RequestBody UserRequestDto dto) {
        return userService.createAdmin(dto);
    }
}