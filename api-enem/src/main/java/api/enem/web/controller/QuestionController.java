package api.enem.web.controller;

import api.enem.service.QuestionService;
import api.enem.web.dto.question.QuestionResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/fetch-and-save")
    @ResponseStatus(HttpStatus.OK)
    public String saveQuestionsForAllExams() {
        return questionService.fetchAndSaveQuestionsForAllExams();
    }
}