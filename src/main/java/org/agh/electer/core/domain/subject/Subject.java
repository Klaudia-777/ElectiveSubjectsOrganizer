package org.agh.electer.core.domain.subject;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
    @Builder.Default
    private List<SubjectChoice> subjectChoices =new ArrayList<>();

    private Description description;
    @Builder.Default
    private List<AlbumNumber> qualifiedStudents=new ArrayList<>();

    public void decreaseNoPlaces() {
        numberOfPlaces = NoPlaces.of(numberOfPlaces.getValue() - 1);
    }
}
