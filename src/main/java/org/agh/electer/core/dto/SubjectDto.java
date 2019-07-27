package org.agh.electer.core.dto;

import lombok.*;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;

import java.util.List;

@Data
@Builder
public class SubjectDto {
    private String id;
    private String name;
    private String tutor;
    private int numberOfPlaces;
    private String description;
    private List<SubjectChoiceDto> subjectChoices;
    private List<StudentDto> qualifiedStudents;
}
