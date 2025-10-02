package api.enem.web.controller;

import api.enem.service.ExamService;
import api.enem.web.dto.exam.ExamResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<ExamResponseDto> getAll(Pageable pageable) {
        return examService.getAll(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fetch")
    public Page<ExamResponseDto> fetchExams(Pageable pageable) {
        return examService.fetchExamsFromApi(pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/fetch-and-save")
    public Page<ExamResponseDto> fetchAndSaveExams(Pageable pageable) {
        return examService.fetchAndSaveExamsFromApi(pageable);
    }
}