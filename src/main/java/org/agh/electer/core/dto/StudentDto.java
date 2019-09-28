package org.agh.electer.core.dto;

import lombok.*;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.entities.TypeOfSemester;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class StudentDto {
    private String albumNumber;
    private String Name;
    private String Surname;
    private StudentsRole studentsRole;
    private FieldOfStudy fieldOfStudy;
    private TypeOfSemester typeOfSemester;
    private StudiesDegree studiesDegree;
    private String Speciality;
    private int numberOfSemester;
    private Double averageGrade;
    private int year;
    @Builder.Default
    private List<SubjectChoiceDto> subjectChoices=new ArrayList<>();
}
