package api.enem.web.mapper;

import api.enem.model.Question;
import api.enem.web.dto.question.QuestionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = QuestionAlternativeMapper.class)
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "exam.id", source = "examId")
    @Mapping(target = "alternatives", source = "alternatives")
    @Mapping(target = "files", source = "files")
    Question toEntity(QuestionResponseDto questionResponseDto);

    @Mapping(target = "examId", source = "exam.id")
    @Mapping(target = "alternatives", source = "alternatives")
    @Mapping(target = "files", source = "files")
    QuestionResponseDto toResponseDto(Question question);
}
