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

    private final ExamRepository examRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String examsUrl = "https://api.enem.dev/v1/exams";

    // Retorna provas salvas
    public Page<ExamResponseDto> getAll(Pageable pageable) {
        return examRepository.findAll(pageable)
                .map(ExamMapper.INSTANCE::toResponseDto);
    }

    // Consome provas da API mas não salva no banco de dados
    public Page<ExamResponseDto> fetchExamsFromApi(Pageable pageable) {
        ExamResponseDto[] examsDto = restTemplate.getForObject(examsUrl, ExamResponseDto[].class);

        if (examsDto == null) {
            return Page.empty(pageable);
        }

        List<ExamResponseDto> page = Arrays.stream(examsDto)
                .toList();

        return new PageImpl<>(page, pageable, examsDto.length);
    }

    // Consome provas da API e salva no banco de dados
    @Transactional
    public Page<ExamResponseDto> fetchAndSaveExamsFromApi(Pageable pageable) {
        ExamResponseDto[] examsDto = restTemplate.getForObject(examsUrl, ExamResponseDto[].class);

        if (examsDto != null) {
            Arrays.stream(examsDto)
                    .map(ExamMapper.INSTANCE::toEntity)
                    .filter(exam -> !existsByTitle(exam.getTitle()))
                    .forEach(examRepository::save);
        }

        return examRepository.findAll(pageable)
                .map(ExamMapper.INSTANCE::toResponseDto);
    }

    private boolean existsByTitle(String title) {
        return examRepository.existsByTitle(title);
    }
}