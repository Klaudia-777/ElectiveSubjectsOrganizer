package org.agh.electer.core.controllers;


import lombok.extern.slf4j.Slf4j;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.agh.electer.core.service.CsvParser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.security.auth.Subject;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api")
public class SubjectController {
    private CsvParser csvParser;
    private SubjectPoolRepository subjectPoolRepository;

    public SubjectController(CsvParser csvParser, SubjectPoolRepository subjectPoolRepository) {
        this.csvParser = csvParser;
        this.subjectPoolRepository = subjectPoolRepository;
    }

    @PostMapping("/subjects")
    public void uploadSubjectsFile(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        SubjectPool subjectPool = csvParser.parseSubjectFile(multipartFile,subjectPoolRepository);
        subjectPoolRepository.update(subjectPool);
    }

}
