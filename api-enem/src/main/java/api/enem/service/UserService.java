package api.enem.service;

import api.enem.model.User;
import api.enem.model.enums.Role;
import api.enem.repository.UserRepository;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import api.enem.web.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto getById(Long id) {
        User user = findById(id);
        return UserMapper.INSTANCE.toResponseDto(user);
    }

    public Page<UserResponseDto> getAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(UserMapper.INSTANCE::toResponseDto);
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
        return UserMapper.INSTANCE.toResponseDto(updatedUser);
    }

    private UserResponseDto saveUserWithRole(UserRequestDto dto, Role role) {
        User user = UserMapper.INSTANCE.toEntity(dto);
        user.setRole(role);
        userRepository.save(user);
        return UserMapper.INSTANCE.toResponseDto(user);
    }

    private void findByEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("User with email '" + email + "' already exists");
        }
    }

    private User findById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found with id " + id));
    }
}