package org.agh.electer.core.controllers;


import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.dto.AdminCredentialsDTO;
import org.agh.electer.core.dto.StudentDto;
import org.agh.electer.core.infrastructure.dao.AdminDao;
import org.agh.electer.core.infrastructure.dtoMappers.StudentDTOMapper;
import org.agh.electer.core.infrastructure.entities.AdminEntity;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.mappers.StudentMapper;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
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

    @Autowired
    public AdminController(AdminDao adminDao, StudentRepository studentRepository) {
        this.adminDao = adminDao;
        this.studentRepository = studentRepository;
    }

    @PostMapping("/admin/login")
    public boolean login(@RequestBody AdminCredentialsDTO credentialsDTO) {
        val result = adminDao.findById(credentialsDTO.getLogin())
                .filter(s -> checkIfCorrectAdminLogin(credentialsDTO, s))
                .isPresent();
        log.info("Czy admin sie zalogował: " + result);
        return result;
    }
    @GetMapping("/admin/fieldOfStudyView/{fieldOfStudy}")
    public List<StudentDto> sendStudentsForThisFOS(@PathVariable String fieldOfStudy) {
        List<StudentDto> result = studentRepository.getAll()
                .stream()
                .filter(s->s.getFieldOfStudy().equals(FieldOfStudy.valueOf(fieldOfStudy)))
                .map(StudentDTOMapper::toDto)
                .collect(Collectors.toList());
        result.forEach(System.out::println);
        return result;
        }

    private boolean checkIfCorrectAdminLogin(final AdminCredentialsDTO credentialsDTO, final AdminEntity adminEntity) {
        return credentialsDTO.getPassword().equals(adminEntity.getPassword());
    }
}
