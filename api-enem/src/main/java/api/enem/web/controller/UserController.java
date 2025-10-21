package api.enem.web.controller;

import api.enem.service.UserService;
import api.enem.web.dto.user.ChangePasswordRequestDto;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        UserResponseDto userResponseDto = userService.getById(id);
        return ResponseEntity.ok(userResponseDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getAll(Pageable pageable) {
        Page<UserResponseDto> page = userService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserResponseDto create(@Valid @RequestBody UserRequestDto dto) {
        return userService.create(dto);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @Valid @RequestBody UserRequestDto dto) {
        return userService.update(id, dto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequestDto dto) {
        userService.changePassword(id, dto);
    }
}