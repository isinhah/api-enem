package api.enem.web.dto.question;

import api.enem.model.enums.AlternativeOption;
import com.fasterxml.jackson.annotation.JsonProperty;

public record QuestionAlternativeResponseDto(
        AlternativeOption letter,
        String text,
        String file,
        @JsonProperty("isCorrect") boolean correct
) {
}