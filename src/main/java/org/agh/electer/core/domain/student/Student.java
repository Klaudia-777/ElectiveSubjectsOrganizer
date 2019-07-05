package org.agh.electer.core.domain.student;

import lombok.Builder;
import lombok.Data;
import javax.validation.constraints.NotNull;

import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import java.util.List;

@Data
@Builder
public class Student {
    @NotNull
    private AlbumNumber albumNumber;

    @NotNull
    private Name Name;

    @NotNull
    private Surname Surname;

    @NotNull
    private StudentsRole studentsRole;

    @NotNull
    private FieldOfStudy fieldOfStudy;

    @NotNull
    private StudiesDegree studiesDegree;

    @NotNull
    private Speciality Speciality;

    @NotNull
    private NoSemester numberOfSemester;

    @NotNull
    private AverageGrade averageGrade;

    @NotNull
    private List<SubjectChoiceId> subjectChoices;

}
