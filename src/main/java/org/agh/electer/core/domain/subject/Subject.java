package org.agh.electer.core.domain.subject;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class Subject {

    @NotNull
    private SubjectId subjectId;

    @NotNull
    private Name name;

    @NotNull
    private Tutor tutor;

    @NotNull
    private NoPlaces numberOfPlaces;

    @NotNull
    private List<SubjectChoice> subjectChoices;

    private Description description;
}
