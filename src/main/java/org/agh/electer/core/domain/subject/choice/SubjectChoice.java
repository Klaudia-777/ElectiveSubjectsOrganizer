package org.agh.electer.core.domain.subject.choice;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.student.AlbumNumber;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class SubjectChoice {
    @NotNull
    private SubjectChoiceId id;

    public Priority getPriority() {
        return priority;
    }

    @NotNull
    private Priority priority;

    @NotNull
    private AlbumNumber student;

}
