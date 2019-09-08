package org.agh.electer.core.domain.subject.choice;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.SubjectId;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SubjectChoice {
    @NotNull
    private SubjectChoiceId id;

    @NotNull
    private Priority priority;

    @NotNull
    private SubjectId subjectId;

    @NotNull
    private AlbumNumber studentId;

    @NotNull
    private QualifiedOrNot qualifiedOrNot;

    public Priority getPriority() {
        return priority;
    }

}
