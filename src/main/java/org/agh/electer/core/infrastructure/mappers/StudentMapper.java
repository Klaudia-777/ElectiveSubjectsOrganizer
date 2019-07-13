package org.agh.electer.core.infrastructure.mappers;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.infrastructure.entities.*;

import java.util.stream.Collectors;


@UtilityClass
public class StudentMapper {
    public StudentEntity toEntity(final Student student) {
        val entity = StudentEntity.builder()
                .albumNumber(student.getAlbumNumber().getValue())
                .Name(student.getName().getValue())
                .Surname(student.getSurname().getValue())
                .fieldOfStudy(student.getFieldOfStudy())
                .numberOfSemester(student.getNumberOfSemester().getValue())
                .Speciality(student.getSpeciality().getValue())
                .averageGrade(student.getAverageGrade().getValue())
                .studentsRole(student.getStudentsRole())
                .studiesDegree(student.getStudiesDegree())
                .subjectChoices(student.getSubjectChoices().keySet())
                .build();


        return entity;
    }

    public Student toDomain(final StudentEntity studentEntity) {
        val student = Student.builder()
                .albumNumber(AlbumNumber.of(studentEntity.getAlbumNumber()))
                .Name(Name.of(studentEntity.getName()))
                .Surname(Surname.of(studentEntity.getSurname()))
                .fieldOfStudy(studentEntity.getFieldOfStudy())
                .numberOfSemester(NoSemester.of(studentEntity.getNumberOfSemester()))
                .Speciality(Speciality.of(studentEntity.getSpeciality()))
                .averageGrade(AverageGrade.of(studentEntity.getAverageGrade()))
                .studentsRole(studentEntity.getStudentsRole())
                .studiesDegree(studentEntity.getStudiesDegree())
                .subjectChoices(studentEntity.getSubjectChoices()
                        .stream()
                        .collect(Collectors.toMap(s->SubjectChoiceId.of(s.getId()),s-> Priority.of(s.getPriority()))))
                .build();
        return student;
    }
}
