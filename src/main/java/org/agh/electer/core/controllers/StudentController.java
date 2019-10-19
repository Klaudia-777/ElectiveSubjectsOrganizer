package org.agh.electer.core.controllers;

import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.dto.CredentialsDTO;
import org.agh.electer.core.dto.SubjectChoiceDto;
import org.agh.electer.core.dto.SubjectDto;
import org.agh.electer.core.dto.SubjectPoolDto;
import org.agh.electer.core.infrastructure.dtoMappers.SubjectChoiceDTOMapper;
import org.agh.electer.core.infrastructure.dtoMappers.SubjectPoolDTOMapper;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.mappers.StudentMapper;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.agh.electer.core.service.CsvParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
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
        Optional.ofNullable(csvParser.parseStudentFile(multipartFile, studentRepository))
                .ifPresent(n -> n.forEach(studentRepository::save));
    }

    @PostMapping("/students/{albumNr}/saveChoices")
    public void saveSubjectChoices(@RequestBody List<SubjectChoiceDto> priorities, @PathVariable String albumNr) {
        log.info(albumNr);
        log.info(priorities.get(1).toString());

        val choices = priorities.stream().map(SubjectChoiceDTOMapper::toDomain)
                .peek(n -> n.setId(SubjectChoiceId.of(UUID.randomUUID().toString()))).collect(Collectors.toList());

        Student student = studentRepository.findById(AlbumNumber.of(albumNr)).get();
        student.setSubjectChoices(choices);
        studentRepository.update(student);

        SubjectPool subjectPool = subjectPoolRepository.findByFieldOfStudy(student.getFieldOfStudy(),
                NoSemester.of(student.getNumberOfSemester().getValue()),
                student.getStudiesDegree());
        subjectPool.getElectiveSubjects().forEach(n -> n.setSubjectChoices(choices));
        subjectPoolRepository.update(subjectPool);

        log.info(student.getSubjectChoices().toString());
    }

    @PostMapping("/students/login")
    public boolean login(@RequestBody CredentialsDTO credentialsDTO) {
        val result = studentRepository.findById(AlbumNumber.of(credentialsDTO.getAlbumNumber()))
                .filter(s -> checkIfStudentExists(credentialsDTO, s))
                .isPresent();
        log.info("Oto wynik: " + result);
        return result;
    }

    @PostMapping("/students/{albumNr}/choices")
    public List<SubjectChoiceDto> showSubjectChoices(@PathVariable String albumNr) {
        val result = studentRepository.findById(AlbumNumber.of(albumNr))
                .get().getSubjectChoices().stream().map(SubjectChoiceDTOMapper::toDto).collect(Collectors.toList());
        log.info(result.toString());
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
    public List<FieldOfStudy> getAllFieldsOfStudy() {
        return List.of(FieldOfStudy.values());
    }

    @PostMapping("/fieldOfStudy")
    public String getFieldOfStudyContent(final FieldOfStudy fieldOfStudy) {
        return fieldOfStudy.toString();
    }

    @GetMapping("/subjectPool/{albumNumber}")
    public Set<SubjectDto> getSubjectPoolForStudent(@PathVariable String albumNumber) {
        return subjectPoolRepository
                .getAll()
                .stream()
                .filter(sp -> sp.getStudents().contains(AlbumNumber.of(albumNumber)))
                .map(SubjectPoolDTOMapper::toDto)
                .map(SubjectPoolDto::getElectiveSubjects)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

}
