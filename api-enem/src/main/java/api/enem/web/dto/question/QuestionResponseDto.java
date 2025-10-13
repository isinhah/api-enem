package api.enem.web.dto.question;

import java.util.List;
import java.util.UUID;

public record QuestionResponseDto(
        UUID examId,
        String title,
        Integer index,
        String discipline,
        String language,
        String context,
        List<String> files,
        String correctAlternative,
        String alternativesIntroduction,
        List<QuestionAlternativeResponseDto> alternatives
) {
}