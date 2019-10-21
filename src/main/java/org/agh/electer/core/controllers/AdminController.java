package org.agh.electer.core.controllers;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.dto.AdminCredentialsDTO;
import org.agh.electer.core.dto.StudentDto;
import org.agh.electer.core.infrastructure.dao.AdminDao;
import org.agh.electer.core.infrastructure.dtoMappers.StudentDTOMapper;
import org.agh.electer.core.infrastructure.entities.AdminEntity;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.agh.electer.core.service.ComputingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class AdminController {
    private AdminDao adminDao;
    private StudentRepository studentRepository;
    private SubjectPoolRepository subjectPoolRepository;
    private ComputingService computingService;

    @Autowired
    public AdminController(AdminDao adminDao, StudentRepository studentRepository, SubjectPoolRepository subjectPoolRepository, ComputingService computingService) {
        this.adminDao = adminDao;
        this.studentRepository = studentRepository;
        this.subjectPoolRepository = subjectPoolRepository;
        this.computingService = computingService;
    }

    @PostMapping("/admin/login")
    public boolean login(@RequestBody AdminCredentialsDTO credentialsDTO) {
        val result = adminDao.findById(credentialsDTO.getLogin())
                .filter(s -> checkIfCorrectAdminLogin(credentialsDTO, s))
                .isPresent();
        log.info("Czy admin sie zalogował: " + result);
        return result;
    }

    @PostMapping("/admin/{fieldOfStudy}/{noSem}/{stDegree}/start")
    public void compute(@PathVariable String fieldOfStudy, @PathVariable String noSem, @PathVariable String stDegree) {
        SubjectPool subjectPool = subjectPoolRepository.findByFieldOfStudy(FieldOfStudy.valueOf(fieldOfStudy),
                NoSemester.of(Integer.valueOf(noSem)),
                StudiesDegree.valueOf(stDegree));
        computingService.compute(subjectPool.getId());
        log.info("zapisano");
    }

    @GetMapping("/admin/fieldOfStudyView/{fieldOfStudy}")
    public List<StudentDto> sendStudentsForThisFOS(@PathVariable String fieldOfStudy) {
        List<StudentDto> result = studentRepository.getAll()
                .stream()
                .filter(s -> s.getFieldOfStudy().equals(FieldOfStudy.valueOf(fieldOfStudy)))
                .map(StudentDTOMapper::toDto)
                .collect(Collectors.toList());
        result.forEach(System.out::println);
        return result;
    }

    private boolean checkIfCorrectAdminLogin(final AdminCredentialsDTO credentialsDTO, final AdminEntity adminEntity) {
        return credentialsDTO.getPassword().equals(adminEntity.getPassword());
    }
}
