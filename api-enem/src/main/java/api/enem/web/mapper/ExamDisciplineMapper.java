package api.enem.web.mapper;

import api.enem.model.ExamDiscipline;
import api.enem.web.dto.exam.ExamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExamDisciplineMapper {

    @Mapping(target = "label", source = "label")
    @Mapping(target = "value", source = "value")
    ExamDiscipline toEntity(ExamResponseDto.Discipline dto);

    ExamResponseDto.Discipline toResponseDto(ExamDiscipline entity);
}
