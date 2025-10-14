package api.enem.service;

import api.enem.model.Exam;
import api.enem.model.Question;
import api.enem.model.QuestionAlternative;
import api.enem.repository.ExamRepository;
import api.enem.repository.QuestionRepository;
import api.enem.web.dto.external_api.QuestionApiResponse;
import api.enem.web.dto.question.QuestionResponseDto;
import api.enem.web.mapper.QuestionAlternativeMapper;
import api.enem.web.mapper.QuestionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final ExamRepository examRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final QuestionAlternativeMapper questionAlternativeMapper;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String questionsUrl = "https://api.enem.dev/v1/exams/{year}/questions";

    public Page<QuestionResponseDto> getByDiscipline(String discipline, Pageable pageable) {
        Page<Question> questions = questionRepository.findByDiscipline(discipline, pageable);

        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions found for discipline: " + discipline);
        }

        return questions.map(questionMapper::toResponseDto);
    }

    public Page<QuestionResponseDto> getByContext(String context, Pageable pageable) {
        Page<Question> questions = questionRepository.findByContextContainingIgnoreCase(context, pageable);

        if (questions.isEmpty()) {
            throw new IllegalStateException("No questions found for context: " + context);
        }

        return questions.map(questionMapper::toResponseDto);
    }

    private QuestionApiResponse callApi(Integer year) {
        return restTemplate.getForObject(questionsUrl, QuestionApiResponse.class, year);
    }

    @Async
    @Transactional
    public void fetchAndSaveQuestionsForAllExams() {
        List<Exam> exams = examRepository.findAll();

        if (exams.isEmpty()) {
            throw new IllegalStateException("No exams found in the database. Please save exams before fetching questions.");
        }

        for (Exam exam : exams) {
            try {
                System.out.println("Looking for exam questions of " + exam.getYear() + "...");
                fetchAndSaveQuestionsFromApi(exam.getYear());
                Thread.sleep(7000);
            } catch (HttpClientErrorException.TooManyRequests e) {
                System.out.println("Limit reached! Waiting 10 seconds...");
                try { Thread.sleep(10000); } catch (InterruptedException ignored) {}
            } catch (Exception e) {
                System.out.println("Error saving exam questions of " + exam.getYear() + ": " + e.getMessage());
            }
        }

        System.out.println("Finished saving all questions!");
    }

    @Transactional
    public void fetchAndSaveQuestionsFromApi(Integer year) {
        QuestionApiResponse response = callApi(year);

        if (response == null || response.questions().isEmpty()) {
            return;
        }

        Exam exam = examRepository.findByYear(year)
                .orElseThrow(() -> new RuntimeException("Exam not found for year " + year));

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
