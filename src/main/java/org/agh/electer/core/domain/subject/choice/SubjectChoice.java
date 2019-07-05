package org.agh.electer.core.domain.subject.choice;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SubjectChoice {
    @NotNull
    private Id id;

    @NotNull
    private Priority priority;

    @NotNull
    private SubjectId subject;

    @NotNull
    private StudentId student;
}
