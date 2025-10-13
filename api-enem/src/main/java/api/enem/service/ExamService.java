package api.enem.service;

import api.enem.model.Exam;
import api.enem.repository.ExamRepository;
import api.enem.web.dto.exam.ExamResponseDto;
import api.enem.web.mapper.ExamMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@RequiredArgsConstructor
@Service
public class ExamService {

    private final ExamMapper examMapper;
    private final ExamRepository examRepository;

    private final RestTemplate restTemplate = new RestTemplate();
    private final String examsUrl = "https://api.enem.dev/v1/exams";

    public Page<ExamResponseDto> getAll(Pageable pageable) {
        return examRepository.findAll(pageable)
                .map(examMapper::toResponseDto);
    }

    public ExamResponseDto getByYear(int year) {
        Exam exam = examRepository.findByYear(year).orElseThrow(
                () -> {throw new RuntimeException("No exams found in the database.");}
        );

        return examMapper.toResponseDto(exam);
    }

    @Transactional
    public Page<ExamResponseDto> fetchAndSaveExamsFromApi(Pageable pageable) {
        ExamResponseDto[] examsDto = restTemplate.getForObject(examsUrl, ExamResponseDto[].class);

        if (examsDto != null) {
            Arrays.stream(examsDto)
                    .map(examMapper::toEntity)
                    .filter(exam -> !existsByTitle(exam.getTitle()))
                    .forEach(examRepository::save);
        }

        return examRepository.findAll(pageable)
                .map(examMapper::toResponseDto);
    }

    private boolean existsByTitle(String title) {
        return examRepository.existsByTitle(title);
    }
}