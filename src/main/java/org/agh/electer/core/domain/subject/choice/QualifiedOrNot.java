package org.agh.electer.core.domain.subject.choice;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(staticName = "of")
public class QualifiedOrNot {
    @NotNull
    private boolean value;
}
