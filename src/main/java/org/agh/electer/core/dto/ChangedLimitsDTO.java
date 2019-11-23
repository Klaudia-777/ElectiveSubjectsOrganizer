package org.agh.electer.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangedLimitsDTO {
    private String subjectId;
    private Integer limit;
}
