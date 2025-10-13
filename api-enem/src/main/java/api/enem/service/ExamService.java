package api.enem.service;

import api.enem.repository.ExamRepository;
import api.enem.web.dto.exam.ExamResponseDto;
import api.enem.web.mapper.ExamMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

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