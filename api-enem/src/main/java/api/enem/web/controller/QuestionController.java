package api.enem.web.controller;

import api.enem.service.QuestionService;
import api.enem.web.dto.external_api.QuestionPageResponse;
import api.enem.web.dto.question.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/discipline/{discipline}")
    public ResponseEntity<QuestionPageResponse> getByDiscipline(
            @PathVariable String discipline,
            @PageableDefault(size = 10, page = 0) Pageable pageable
    ) {
        QuestionPageResponse response = questionService.getByDiscipline(discipline, pageable);
        return ResponseEntity.ok(response);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/context/{context}")
    public ResponseEntity<QuestionPageResponse> getByContext(
            @PathVariable String context,
            @PageableDefault(size = 10, page = 0) Pageable pageable) {
        QuestionPageResponse response = questionService.getByContext(context, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/fetch-and-save")
    @ResponseStatus(HttpStatus.OK)
    public String fetchAndSaveQuestions() {
        questionService.fetchAndSaveQuestionsForAllExams();
        return "Process started in background. Check logs for progress.";
    }
}