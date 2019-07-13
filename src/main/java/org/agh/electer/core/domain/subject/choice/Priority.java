package org.agh.electer.core.domain.subject.choice;

import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.util.Comparator;

@Value
@AllArgsConstructor(staticName = "of")
public class Priority implements Comparator<Priority> {
    @NotNull
    private int value;

    @Override
    public int compare(Priority o1, Priority o2) {
        return Integer.compare(o1.getValue(),o2.getValue());
    }
}
