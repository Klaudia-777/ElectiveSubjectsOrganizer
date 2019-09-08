package org.agh.electer.core.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.dto.CredentialsDTO;
import org.agh.electer.core.dto.SubjectPoolDto;
import org.agh.electer.core.infrastructure.dtoMappers.SubjectPoolDTOMapper;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class StudentController {
    private StudentRepository studentRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, SubjectPoolRepository subjectPoolRepository) {
        this.studentRepository = studentRepository;
        this.subjectPoolRepository = subjectPoolRepository;
    }

    private SubjectPoolRepository subjectPoolRepository;


    @PostMapping("/students/login")
    public boolean login(@RequestBody CredentialsDTO credentialsDTO) {
        val result = studentRepository.findById(AlbumNumber.of(credentialsDTO.getAlbumNumber()))
                .filter(s -> checkIfStudentExists(credentialsDTO,s))
                .isPresent();
        log.info("Oto wynik: " + result);
        return result;
    }

    private boolean checkIfStudentExists(final CredentialsDTO credentials, final Student student) {
        if (credentials.getAverageGrade() == student.getAverageGrade().getValue()
                && credentials.getName().equals(student.getName().getValue())
                && credentials.getSurname().equals(student.getSurname().getValue())
                && credentials.getFieldOfStudy() == student.getFieldOfStudy()
                && credentials.getNumberOfSemester() == student.getNumberOfSemester().getValue()
//                && credentials.getSpeciality().equals(student.getSpeciality().getValue())
                && credentials.getStudiesDegree() == student.getStudiesDegree()) {
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/xdc")
    public String xd() {
        return "xd";
    }

    @GetMapping("/subjectPool")
    public List<SubjectPoolDto> getSubjectPoolForStudent(String albumNumber) {
        return subjectPoolRepository
                .getAll()
                .stream()
                .filter(sp -> sp.getStudents().contains(AlbumNumber.of(albumNumber)))
                .map(SubjectPoolDTOMapper::toDto)
                .collect(Collectors.toList());
    }

}
