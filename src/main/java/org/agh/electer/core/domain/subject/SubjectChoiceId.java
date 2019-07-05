package org.agh.electer.core.domain.subject;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(staticName = "of")
public class SubjectChoiceId {
    @NotNull
    private String value;
}
