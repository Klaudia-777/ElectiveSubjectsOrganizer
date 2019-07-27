package org.agh.electer.core.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor(staticName = "of")
@Getter
@NoArgsConstructor
@Setter
@Builder
public class SubjectDto {
    private String id;
    private String name;
    private String tutor;
    private int numberOfPlaces;
    private String description;
    private SubjectPoolDto subjectPool;
    private List<StudentDto> qualifiedStudents;
}
