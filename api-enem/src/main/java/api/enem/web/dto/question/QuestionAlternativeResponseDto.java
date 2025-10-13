package api.enem.web.dto.question;

import api.enem.model.enums.AlternativeOption;

public record QuestionAlternativeResponseDto(
        AlternativeOption letter,
        String text,
        String file,
        boolean correct
) {
}