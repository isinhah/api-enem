package api.enem.web.controller;

import api.enem.service.QuestionService;
import api.enem.web.dto.question.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/by-discipline")
    public Page<QuestionResponseDto> getByDiscipline(@RequestParam String discipline, Pageable pageable) {
        return questionService.getByDiscipline(discipline, pageable);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/by-context")
    public Page<QuestionResponseDto> getByContext(@RequestParam String context, Pageable pageable) {
        return questionService.getByContext(context, pageable);
    }

    @GetMapping("/fetch-and-save")
    @ResponseStatus(HttpStatus.OK)
    public String fetchAndSaveQuestions() {
        questionService.fetchAndSaveQuestionsForAllExams();
        return "Process started in background. Check logs for progress.";
    }
}