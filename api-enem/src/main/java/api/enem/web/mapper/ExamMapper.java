package api.enem.web.mapper;

import api.enem.model.Exam;
import api.enem.web.dto.exam.ExamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ExamDisciplineMapper.class, ExamLanguageMapper.class})
public interface ExamMapper {

    ExamMapper INSTANCE = Mappers.getMapper(ExamMapper.class);

    @Mapping(target = "id", ignore = true)
    Exam toEntity(ExamResponseDto examResponseDto);

    ExamResponseDto toResponseDto(Exam exam);
}