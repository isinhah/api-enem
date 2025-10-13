package api.enem.web.mapper;

import api.enem.model.Exam;
import api.enem.web.dto.exam.ExamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ExamDisciplineMapper.class, ExamLanguageMapper.class})
public interface ExamMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "year", source = "year")
    @Mapping(target = "disciplines", source = "disciplines")
    @Mapping(target = "languages", source = "languages")
    Exam toEntity(ExamResponseDto examResponseDto);

    ExamResponseDto toResponseDto(Exam exam);
}