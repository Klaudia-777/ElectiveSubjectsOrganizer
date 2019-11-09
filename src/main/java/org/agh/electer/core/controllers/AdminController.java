package org.agh.electer.core.controllers;


import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.domain.student.Student;
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
import org.agh.electer.core.service.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class AdminController {
    private AdminDao adminDao;
    private StudentRepository studentRepository;
    private SubjectPoolRepository subjectPoolRepository;
    private ComputingService computingService;
    private CsvParser csvParser;

    @Autowired
    public AdminController(AdminDao adminDao, StudentRepository studentRepository, SubjectPoolRepository subjectPoolRepository, ComputingService computingService, CsvParser csvParser) {
        this.adminDao = adminDao;
        this.studentRepository = studentRepository;
        this.subjectPoolRepository = subjectPoolRepository;
        this.computingService = computingService;
        this.csvParser = csvParser;
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

    @GetMapping("/admin/{fieldOfStudy}/{noSem}/{stDegree}/download")
    public void generate(@PathVariable String fieldOfStudy,
                         @PathVariable String noSem,
                         @PathVariable String stDegree,
                         HttpServletResponse httpServletResponse) throws CsvRequiredFieldEmptyException, IOException, CsvDataTypeMismatchException {
        List<Student> students=studentRepository.findByFieldOfStudy(FieldOfStudy.valueOf(fieldOfStudy),
                NoSemester.of(Integer.valueOf(noSem)),
                StudiesDegree.valueOf(stDegree));

        Optional.ofNullable(subjectPoolRepository.findByFieldOfStudy(FieldOfStudy.valueOf(fieldOfStudy),
                NoSemester.of(Integer.valueOf(noSem)),
                StudiesDegree.valueOf(stDegree))).ifPresent(subjectPool -> {
            try {
                csvParser.generateFile(httpServletResponse, students,subjectPool);
            } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
                e.printStackTrace();
            }
        });

        ;
        log.info("pobrano");
    }

    @GetMapping("/admin/fieldOfStudyView/{fieldOfStudy}/{studiesDegree}/{numberOfSemester}")
    public List<StudentDto> sendStudentsForThisFOS(@PathVariable String fieldOfStudy,@PathVariable String studiesDegree,@PathVariable String numberOfSemester) {
        List<StudentDto> result = studentRepository.getAll()
                .stream()
                .filter(s -> s.getFieldOfStudy().equals(FieldOfStudy.valueOf(fieldOfStudy)))
                .filter(n->n.getStudiesDegree().equals(StudiesDegree.valueOf(studiesDegree)))
                .filter(m->m.getNumberOfSemester().getValue()==Integer.valueOf(numberOfSemester))
                .map(StudentDTOMapper::toDto)
                .collect(Collectors.toList());
        result.forEach(System.out::println);
        return result;
    }

    private boolean checkIfCorrectAdminLogin(final AdminCredentialsDTO credentialsDTO, final AdminEntity adminEntity) {
        return credentialsDTO.getPassword().equals(adminEntity.getPassword());
    }
}
