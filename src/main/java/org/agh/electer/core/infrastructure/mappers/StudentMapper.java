package org.agh.electer.core.infrastructure.mappers;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.domain.subject.SubjectId;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.infrastructure.entities.*;

import java.util.Optional;
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
                .Speciality(Optional.ofNullable(student.getSpeciality()).map(Speciality::getValue).orElse(null))
                .averageGrade(Optional.ofNullable(student.getAverageGrade()).map(AverageGrade::getValue).orElse(null))
                .studentsRole(student.getStudentsRole())
                .studiesDegree(student.getStudiesDegree())
                .year(student.getYear().getValue())
                .typeOfSemester(student.getTypeOfSemester())
                .build();

        val choices = student.getSubjectChoices().stream().map(SubjectChoiceMapper::toEntity).collect(Collectors.toList());

        entity.setSubjectChoices(choices);
        return entity;
    }


    public Student toDomain(final StudentEntity studentEntity) {
        val student = Student.builder()
                .albumNumber(AlbumNumber.of(studentEntity.getAlbumNumber()))
                .Name(Name.of(studentEntity.getName()))
                .Surname(Surname.of(studentEntity.getSurname()))
                .fieldOfStudy(studentEntity.getFieldOfStudy())
                .numberOfSemester(NoSemester.of(studentEntity.getNumberOfSemester()))
                .Speciality(Optional.ofNullable(studentEntity.getSpeciality()).map(Speciality::of).orElse(null))
                .averageGrade(Optional.ofNullable(studentEntity.getAverageGrade()).map(AverageGrade::of).orElse(null))
                .studentsRole(studentEntity.getStudentsRole())
                .studiesDegree(studentEntity.getStudiesDegree())
                .year(YearOfStudies.of(studentEntity.getYear()))
                .typeOfSemester(studentEntity.getTypeOfSemester())
                .subjectChoices(studentEntity.getSubjectChoices().stream()
                        .map(SubjectChoiceMapper::toDomain)
                        .collect(Collectors.toList()))
                .build();
        return student;
    }

}
