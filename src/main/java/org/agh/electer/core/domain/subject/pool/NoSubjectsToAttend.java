package org.agh.electer.core.domain.subject.pool;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(staticName = "of")
public class NoSubjectsToAttend {
    @NotNull
    private int value;
}
