package org.agh.electer.core.domain.subject;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class Subject {

    @NotNull
    private SubjectId subjectId;

    @NotNull
    private SubjectName subjectName;

    @NotNull
    private Tutor tutor;

    @NotNull
    private NoPlaces numberOfPlaces;

    @NotNull
    private List<SubjectChoice> subjectChoices;

    private Description description;

    private List<Student> qualifiedStudents;

    public void decreaseNoPlaces() {
        numberOfPlaces = NoPlaces.of(numberOfPlaces.getValue() - 1);
    }
}
