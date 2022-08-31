package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.LanguageDto;
import victor.training.spring.web.entity.ProgrammingLanguage;
import victor.training.spring.web.repo.ProgrammingLanguageRepo;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/languages")
public class LanguageController {
    private final LanguageService languageService;
    @GetMapping
    // TODO cache
    // TODO evict via dedicated endpoint (called from script)
    public List<LanguageDto> getAll() {
        return languageService.getEntities().stream().map(LanguageDto::new).collect(Collectors.toList());
    }

}

@Service
@RequiredArgsConstructor
class LanguageService  {
    private final ProgrammingLanguageRepo repo;

    @Cacheable("languages")
    public List<ProgrammingLanguage> getEntities() {
        return repo.findAll();
    }
}


@RestController
@RequiredArgsConstructor
class AnotherController {
    private final LanguageService languageService;

    @GetMapping("two")
    public List<ProgrammingLanguage> two() {
        return languageService.getEntities();
    }
}


