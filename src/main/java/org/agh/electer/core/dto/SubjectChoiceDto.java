package org.agh.electer.core.dto;

import lombok.*;

@AllArgsConstructor(staticName = "of")
@Getter
@NoArgsConstructor
@Setter
@Builder
public class SubjectChoiceDto {
    private String id;
    private int priority;
    private String studentId;
    private String subjectId;
}
