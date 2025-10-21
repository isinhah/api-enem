package api.enem.service;

import api.enem.model.User;
import api.enem.model.enums.Role;
import api.enem.repository.UserRepository;
import api.enem.web.dto.user.ChangePasswordRequestDto;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import api.enem.web.exception.UserAlreadyExists;
import api.enem.web.exception.UserNotFoundException;
import api.enem.web.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserResponseDto getById(Long id) {
        User user = findById(id);
        return userMapper.toResponseDto(user);
    }

    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponseDto);
    }

    @Transactional
    public UserResponseDto create(UserRequestDto dto) {
        findByEmail(dto.email());
        return saveUserWithRole(dto, Role.USER);
    }

    @Transactional
    public UserResponseDto createAdmin(UserRequestDto dto) {
        findByEmail(dto.email());
        return saveUserWithRole(dto, Role.ADMIN);
    }

    @Transactional
    public UserResponseDto update(Long id, UserRequestDto dto) {
        User user = findById(id);

        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        User updatedUser = userRepository.save(user);
        return userMapper.toResponseDto(updatedUser);
    }

    @Transactional
    public void changePassword(Long id, ChangePasswordRequestDto dto) {
        User user = findById(id);

        if (!dto.currentPassword().equals(user.getPassword())) {
            throw new RuntimeException("Current password is incorrect.");
        }

        user.setPassword(dto.newPassword());
        userRepository.save(user);
    }

    private UserResponseDto saveUserWithRole(UserRequestDto dto, Role role) {
        User user = userMapper.toEntity(dto);
        user.setRole(role);
        userRepository.save(user);
        return userMapper.toResponseDto(user);
    }

    private void findByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExists("User with email '" + email + "' already exists");
        }
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id " + id));
    }
}