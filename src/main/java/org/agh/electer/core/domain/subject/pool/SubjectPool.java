package org.agh.electer.core.domain.subject.pool;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class SubjectPool {
    @NotNull
    private SubjectPoolId id;

    @NotNull
    private FieldOfStudy fieldOfStudy;

    @NotNull
    private Set<Subject> electiveSubjects;

    @NotNull
    private NoSubjectsToAttend noSubjectsToAttend;
}
