package org.agh.electer.core.dto;

import lombok.*;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import java.util.Set;

@Data
@Builder
public class SubjectPoolDto {
    private String id;
    private String date;
    private FieldOfStudy fieldOfStudy;
    private Set<SubjectDto> electiveSubjects;
    private Set<String> students;
    private int numberOfSubjectsToAttend;
    private StudiesDegree studiesDegree;
    private int noSemester;
}
