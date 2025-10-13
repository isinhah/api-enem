package api.enem.web.mapper;

import api.enem.model.ExamLanguage;
import api.enem.web.dto.exam.ExamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamLanguageMapper {

    @Mapping(target = "label", source = "label")
    @Mapping(target = "value", source = "value")
    ExamLanguage toEntity(ExamResponseDto.Language dto);

    ExamResponseDto.Language toResponseDto(ExamLanguage entity);
}
