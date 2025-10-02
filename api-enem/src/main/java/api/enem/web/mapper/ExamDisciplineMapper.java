package api.enem.web.mapper;

import api.enem.model.ExamDiscipline;
import api.enem.web.dto.exam.ExamResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExamDisciplineMapper {

    ExamDisciplineMapper INSTANCE = Mappers.getMapper(ExamDisciplineMapper.class);

    @Mapping(target = "label", source = "label")
    @Mapping(target = "value", source = "value")
    ExamDiscipline toEntity(ExamResponseDto.Discipline dto);

    ExamResponseDto.Discipline toDto(ExamDiscipline entity);
}
