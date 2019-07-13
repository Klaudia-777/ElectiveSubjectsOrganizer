package org.agh.electer.core.domain.student;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class Speciality {
    private String value;
}
