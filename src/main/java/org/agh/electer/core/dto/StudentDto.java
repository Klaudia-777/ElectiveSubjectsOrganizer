package org.agh.electer.core.dto;

import lombok.*;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import java.util.List;

@Data
@Builder
public class StudentDto {
    private String albumNumber;
    private String Name;
    private String Surname;
    private StudentsRole studentsRole;
    private FieldOfStudy fieldOfStudy;
    private StudiesDegree studiesDegree;
    private String Speciality;
    private int numberOfSemester;
    private double averageGrade;
    private List<SubjectChoiceDto> subjectChoices;
}
