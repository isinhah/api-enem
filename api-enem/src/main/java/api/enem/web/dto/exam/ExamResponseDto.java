package api.enem.web.dto.exam;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ExamResponseDto(
        UUID id,
        String title,
        int year,
        List<Discipline> disciplines,
        List<Language> languages
) {

    public record Discipline(String label, String value) {}

    public record Language(String label, String value) {}
}