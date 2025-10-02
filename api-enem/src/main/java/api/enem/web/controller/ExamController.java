package api.enem.web.controller;

import api.enem.service.ExamService;
import api.enem.web.dto.exam.ExamResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/exams")
public class ExamController {

    private final ExamService examService;

    @GetMapping
    public ResponseEntity<Page<ExamResponseDto>> getAll(Pageable pageable) {
        Page<ExamResponseDto> page = examService.getAll(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/fetch")
    public ResponseEntity<Page<ExamResponseDto>> fetchExams(Pageable pageable) {
        Page<ExamResponseDto> page = examService.fetchExamsFromApi(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/fetch-and-save")
    public ResponseEntity<Page<ExamResponseDto>> fetchAndSaveExams(Pageable pageable) {
        Page<ExamResponseDto> page = examService.fetchAndSaveExamsFromApi(pageable);
        return ResponseEntity.ok(page);
    }
}