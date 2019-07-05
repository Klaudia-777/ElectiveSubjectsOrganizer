package org.agh.electer.core.domain.subject;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Subject {

    @NotNull
    private Id subjectId;

    @NotNull
    private Name name;

    @NotNull
    private Tutor tutor;

    @NotNull
    private NoPlaces numberOfPlaces;

    @NotNull
    private Description description;

    @NotNull
    private SubjectSetId subjectSet;

    @NotNull
    private SubjectChoiceId subjectChoice;

}
