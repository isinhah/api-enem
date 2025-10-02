package api.enem.web.dto.external_api;

import api.enem.web.dto.question.QuestionResponseDto;

import java.util.List;

public record QuestionApiResponse(
        MetadataDto metadata,
        List<QuestionResponseDto> questions
) {
}
