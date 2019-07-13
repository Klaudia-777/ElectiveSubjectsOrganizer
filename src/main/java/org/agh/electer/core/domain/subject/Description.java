package org.agh.electer.core.domain.subject;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Description {
    private String value;
}
