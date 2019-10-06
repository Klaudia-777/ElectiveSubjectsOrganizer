package org.agh.electer.core.domain.subject.pool;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class NoSemester {
    private Integer value;
}
