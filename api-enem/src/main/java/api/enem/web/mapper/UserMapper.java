package api.enem.web.mapper;

import api.enem.model.User;
import api.enem.web.dto.user.UserRequestDto;
import api.enem.web.dto.user.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "role", ignore = true)
    User toEntity(UserRequestDto requestDto);

    UserResponseDto toResponseDto(User user);
}
