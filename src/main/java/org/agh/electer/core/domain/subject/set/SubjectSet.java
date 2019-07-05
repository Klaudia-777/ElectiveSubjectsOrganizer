package org.agh.electer.core.domain.subject.set;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class SubjectSet {
    @NotNull
    private Id id;

    @NotNull
    private FieldOfStudy fieldOfStudy;

    @NotNull
    private Set<SubjectId> electiveSubjects;
}
