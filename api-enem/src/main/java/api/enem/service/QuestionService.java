package api.enem.service;

import api.enem.model.Exam;
import api.enem.model.Question;
import api.enem.model.QuestionAlternative;
import api.enem.repository.ExamRepository;
import api.enem.repository.QuestionRepository;
import api.enem.web.dto.external_api.QuestionApiResponse;
import api.enem.web.dto.question.QuestionResponseDto;
import api.enem.web.exception.*;
import api.enem.web.mapper.QuestionAlternativeMapper;
import api.enem.web.mapper.QuestionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuestionService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionAlternativeMapper questionAlternativeMapper;

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String API_QUESTIONS_URL = "https://api.enem.dev/v1/exams/{year}/questions";

    public Page<QuestionResponseDto> getByDiscipline(String discipline, Pageable pageable) {
        Page<Question> questions = questionRepository.findByDiscipline(discipline, pageable);

        if (questions.isEmpty()) {
            throw new NoQuestionsFoundException("No questions found for discipline: " + discipline);
        }

        return questions.map(questionMapper::toResponseDto);
    }

    public Page<QuestionResponseDto> getByContext(String context, Pageable pageable) {
        Page<Question> questions = questionRepository.findByContextContainingIgnoreCase(context, pageable);

        if (questions.isEmpty()) {
            throw new NoQuestionsFoundException("No questions found for context: " + context);
        }

        return questions.map(questionMapper::toResponseDto);
    }

    public Page<QuestionResponseDto> getByExamId(UUID examId, Pageable pageable) {
        Page<Question> questions = questionRepository.findByExamId(examId, pageable);

        if (questions.isEmpty()) {
            throw new NoQuestionsFoundException("No questions found for Exam with id: " + examId);
        }

        return questions.map(questionMapper::toResponseDto);
    }

    private QuestionApiResponse callApi(Integer year) {
        return restTemplate.getForObject(API_QUESTIONS_URL, QuestionApiResponse.class, year);
    }

    @Async
    @Transactional
    public void fetchAndSaveQuestionsForAllExams() {
        List<Exam> exams = examRepository.findAll();

        if (exams.isEmpty()) {
            throw new NoExamsFoundException();
        }

        for (Exam exam : exams) {
            processExamQuestions(exam);
        }

        log.info("Finished saving all questions!");
    }

    private void processExamQuestions(Exam exam) {
        try {
            log.info("Fetching questions for exam of {}", exam.getYear());
            fetchAndSaveQuestionsFromApi(exam.getYear());
            Thread.sleep(7000);
        } catch (HttpClientErrorException.TooManyRequests e) {
            handleRateLimit(e);
        } catch (Exception e) {
            log.error("Error while saving questions for exam year {}: {}", exam.getYear(), e.getMessage(), e);
        }
    }

    private void handleRateLimit(HttpClientErrorException.TooManyRequests e) {
        log.warn("API rate limit reached. Waiting 10 seconds before retrying...");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            log.error("Thread interrupted while waiting after rate limit", ie);
        }
    }

    public void fetchAndSaveQuestionsFromApi(Integer year) {
        QuestionApiResponse response = callApi(year);

        if (response == null || response.questions().isEmpty()) {
            throw new NoQuestionsFoundException("No questions returned by external API for year " + year);
        }

        Exam exam = examRepository.findByYear(year)
                .orElseThrow(() -> new NoExamsFoundException("Exam not found for year " + year));

        for (QuestionResponseDto dto : response.questions()) {
            boolean exists = questionRepository.existsByExamAndIndex(exam, dto.index());
            if (!exists) {
                Question question = mapToEntity(dto, exam);
                questionRepository.save(question);
            }
        }
    }

    private Question mapToEntity(QuestionResponseDto dto, Exam exam) {
        Question question = questionMapper.toEntity(dto);

        question.setExam(exam);
        question.setFiles(dto.files());

        List<QuestionAlternative> alternatives = dto.alternatives().stream()
                .map(aDto -> {
                    QuestionAlternative alt = questionAlternativeMapper.toEntity(aDto);
                    alt.setQuestion(question);
                    return alt;
                })
                .toList();

        question.setAlternatives(alternatives);
        return question;
    }
}
