package org.agh.electer.core.domain.student;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.util.Comparator;

@Value
@AllArgsConstructor(staticName = "of")
public class AverageGrade implements Comparator<AverageGrade> {
    private Double value;

    @Override
    public int compare(AverageGrade o1, AverageGrade o2) {
        return Double.compare(o1.getValue(), o2.getValue());
    }
}

