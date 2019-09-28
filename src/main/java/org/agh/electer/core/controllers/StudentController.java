package org.agh.electer.core.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.dto.CredentialsDTO;
import org.agh.electer.core.dto.SubjectDto;
import org.agh.electer.core.dto.SubjectPoolDto;
import org.agh.electer.core.infrastructure.dao.SubjectPoolDao;
import org.agh.electer.core.infrastructure.dtoMappers.SubjectPoolDTOMapper;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.agh.electer.core.service.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api")
public class StudentController {
    private StudentRepository studentRepository;
    private CsvParser csvParser;
    private SubjectPoolRepository subjectPoolRepository;

    @Autowired
    public StudentController(StudentRepository studentRepository, CsvParser csvParser, SubjectPoolRepository subjectPoolRepository) {
        this.studentRepository = studentRepository;
        this.csvParser = csvParser;
        this.subjectPoolRepository = subjectPoolRepository;
    }

    @PostMapping("/students")
    public void uploadStudentsFile(@RequestParam(name = "file") MultipartFile multipartFile) throws IOException {
        csvParser.parseStudentFile(multipartFile,studentRepository).forEach(studentRepository::save);
    }

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

    @GetMapping("/fieldsOfStudy")
    public List<FieldOfStudy>getAllFieldsOfStudy(){
        return List.of(FieldOfStudy.values());
    }

    @PostMapping("/fieldOfStudy")
    public String getFieldOfStudyContent(final FieldOfStudy fieldOfStudy){
        return fieldOfStudy.toString();
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
