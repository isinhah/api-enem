package api.enem.web.dto.exam;

import java.util.List;

public record ExamResponseDto(
        String title,
        int year,
        List<Discipline> disciplines,
        List<Language> languages
) {

    public record Discipline(String label, String value) {}

    public record Language(String label, String value) {}
}