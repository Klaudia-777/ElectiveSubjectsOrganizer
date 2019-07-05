package org.agh.electer.core.domain.subject.choice;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(staticName = "of")
public class Id {
    @NotNull
    private String value;
}
