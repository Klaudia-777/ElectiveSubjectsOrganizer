package org.agh.electer.core.dto;

import lombok.*;

@Data
@Builder
public class SubjectChoiceDto {
    private String id;
    private int priority;
    private String studentId;
    private String subjectId;
    private boolean qualifiedOrNot;
}
