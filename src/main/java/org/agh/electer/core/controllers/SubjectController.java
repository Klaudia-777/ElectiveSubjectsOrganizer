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

import java.io.IOException;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class SubjectController {
    private CsvParser csvParser;
    private SubjectPoolRepository subjectPoolRepository;
    private StudentRepository studentRepository;

    public SubjectController(CsvParser csvParser, SubjectPoolRepository subjectPoolRepository, StudentRepository studentRepository) {
        this.csvParser = csvParser;
        this.subjectPoolRepository = subjectPoolRepository;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/subjects")
    public void uploadSubjectsFile(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        Optional<SubjectPool> subjectPool = Optional.ofNullable(csvParser.parseSubjectFile(multipartFile,subjectPoolRepository,studentRepository));
        subjectPool.ifPresent(subjectPoolRepository::save);
    }
}
