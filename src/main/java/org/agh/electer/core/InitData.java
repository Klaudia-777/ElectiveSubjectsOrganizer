package org.agh.electer.core;

import lombok.val;
import org.agh.electer.core.controllers.StudentController;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.SubjectId;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.dto.SubjectChoiceDto;
import org.agh.electer.core.infrastructure.dao.AdminDao;
import org.agh.electer.core.infrastructure.dao.SubjectChoiceDao;
import org.agh.electer.core.infrastructure.entities.AdminEntity;
import org.agh.electer.core.infrastructure.mappers.SubjectChoiceMapper;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class InitData {
    private final StudentController studentController;
    private final AdminDao adminDao;
    private final SubjectPoolRepository subjectPoolRepository;
    private final StudentRepository studentRepository;
    private final SubjectChoiceDao subjectChoiceDao;
    private final List<String> studentsList = Stream.of("").collect(Collectors.toList());

    @Autowired
    public InitData(StudentController studentController, AdminDao adminDao, SubjectPoolRepository subjectPoolRepository, StudentRepository studentRepository, SubjectChoiceDao subjectChoiceDao) {
        this.studentController = studentController;
        this.adminDao = adminDao;
        this.subjectPoolRepository = subjectPoolRepository;
        this.studentRepository = studentRepository;
        this.subjectChoiceDao = subjectChoiceDao;
    }

    @PostConstruct
    public void setInitialData() {
        val adminEntity = AdminEntity.builder()
                .login("admin")
                .password("admin")
                .build();
        adminDao.save(adminEntity);

        val subjectPool = subjectPoolRepository.findById(SubjectPoolId.of("7202e974-6744-4421-9cbc-8ea38fe20e90")).get();

        val subjectList = subjectPool.getElectiveSubjects().stream()
                .map(Subject::getSubjectId)
                .map(SubjectId::getValue)
                .collect(Collectors.toList());

//        subjectList.stream().map(subjectPoolRepository::selectSubjectChoicesForSubject)
//                .flatMap(Collection::stream).map(SubjectChoiceMapper::toEntity).forEach(subjectChoiceDao::delete);

        val students = subjectPool.getStudents().stream()
                .map(AlbumNumber::getValue)
                .collect(Collectors.toList());

        val priorities = Stream.iterate(1, integer -> integer + 1).limit(subjectList.size()).collect(Collectors.toList());

        Map<String, List<SubjectChoiceDto>> studentsChoices = new HashMap<>();
        for (val student : students) {
            Collections.shuffle(priorities);
            studentsChoices.put(student, new ArrayList<>());
            for (int i = 0; i < subjectList.size(); i++) {
                studentsChoices.get(student).add(setSubjectChoicesOnStartCustomFunction(student, subjectList.get(i), priorities.get(i)));
            }
        }
        studentsChoices.forEach((key, value) -> studentController.saveSubjectChoices(value, key));
    }

    public SubjectChoiceDto setSubjectChoicesOnStartCustomFunction(String studentId,
                                                                   String subjectId,
                                                                   int priority) {

        return SubjectChoiceDto.builder()
                .studentId(studentId)
                .subjectId(subjectId)
                .priority(priority)
                .build();

    }

}
