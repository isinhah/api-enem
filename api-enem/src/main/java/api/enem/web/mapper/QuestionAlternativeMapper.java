package api.enem.web.mapper;

import api.enem.model.QuestionAlternative;
import api.enem.model.enums.AlternativeOption;
import api.enem.web.dto.question.QuestionAlternativeResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface QuestionAlternativeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "question", ignore = true)
    @Mapping(target = "text", source = "text")
    @Mapping(target = "file", source = "file")
    @Mapping(target = "correct", source = "correct")
    @Mapping(target = "letter", source = "letter")
    QuestionAlternative toEntity(QuestionAlternativeResponseDto dto);

    @Mapping(target = "correct", expression = "java(entity.getLetter().name().equals(correctAlternative.name()))")
    QuestionAlternativeResponseDto toDto(QuestionAlternative entity, AlternativeOption correctAlternative);
}