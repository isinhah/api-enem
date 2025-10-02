package api.enem.web.mapper;

import api.enem.model.QuestionAlternative;
import api.enem.web.dto.question.QuestionAlternativeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionAlternativeMapper {

    QuestionAlternativeMapper INSTANCE = Mappers.getMapper(QuestionAlternativeMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    QuestionAlternative toEntity(QuestionAlternativeResponseDto dto);

    QuestionAlternativeResponseDto toDto(QuestionAlternative entity);
}

