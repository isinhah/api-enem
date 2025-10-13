package api.enem.web.controller;

import api.enem.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/fetch-and-save")
    @ResponseStatus(HttpStatus.OK)
    public String saveQuestionsForAllExams() {
        questionService.fetchAndSaveQuestionsForAllExams();
        return "Process started in background. Check logs for progress.";
    }
}