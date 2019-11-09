package org.agh.electer.core.dto;

import com.opencsv.bean.CsvBindByPosition;
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
    //    @CsvBindByPosition(position = 0)
    private String albumNumber;
    //    @CsvBindByPosition(position = 0)
    private String Name;
    //    @CsvBindByPosition(position = 0)
    private String Surname;
    //    @CsvBindByPosition(position = 10)
    private StudentsRole studentsRole;
    //    @CsvBindByPosition(position = 3)
    private FieldOfStudy fieldOfStudy;
    //    @CsvBindByPosition(position = 6)
    private TypeOfSemester typeOfSemester;
    //    @CsvBindByPosition(position = 4)
    private StudiesDegree studiesDegree;
    //    @CsvBindByPosition(position = 7)
    private String Speciality;
    //    @CsvBindByPosition(position = 5)
    private int numberOfSemester;
    //    @CsvBindByPosition(position = 9)
    private Double averageGrade;
    //    @CsvBindByPosition(position = 8)
    private int year;
    @Builder.Default
    private List<SubjectChoiceDto> subjectChoices = new ArrayList<>();
}
