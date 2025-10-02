package api.enem.web.mapper;

import api.enem.model.ExamLanguage;
import api.enem.web.dto.exam.ExamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExamLanguageMapper {

    ExamLanguageMapper INSTANCE = Mappers.getMapper(ExamLanguageMapper.class);

    @Mapping(target = "label", source = "label")
    @Mapping(target = "value", source = "value")
    ExamLanguage toEntity(ExamResponseDto.Language dto);

    ExamResponseDto.Language toDto(ExamLanguage entity);
}
