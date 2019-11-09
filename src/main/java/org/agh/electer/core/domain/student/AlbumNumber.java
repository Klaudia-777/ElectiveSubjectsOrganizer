package org.agh.electer.core.domain.student;

import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Value;

import javax.validation.constraints.NotNull;

@Value
@AllArgsConstructor(staticName = "of")
public class AlbumNumber {
    @NotNull
    private String value;
}
