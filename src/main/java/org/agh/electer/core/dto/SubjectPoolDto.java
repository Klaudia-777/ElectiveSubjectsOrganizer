package org.agh.electer.core.dto;

import lombok.*;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;

import java.util.Set;

@AllArgsConstructor(staticName = "of")
@Getter
@NoArgsConstructor
@Setter
@Builder
public class SubjectPoolDto {
    private String id;
    private FieldOfStudy fieldOfStudy;
    private Set<SubjectDto> electiveSubjects;
    private int numberOfSubjectsToAttend;
}