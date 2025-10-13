package api.enem.web.mapper;

import api.enem.model.User;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "mockExams", ignore = true)
    @Mapping(target = "answers", ignore = true)
    User toEntity(UserRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}
