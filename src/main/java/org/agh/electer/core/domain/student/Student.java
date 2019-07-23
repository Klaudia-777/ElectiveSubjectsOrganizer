package org.agh.electer.core.domain.student;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

import org.agh.electer.core.domain.subject.SubjectId;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Data
@Builder
public class Student {
    @NotNull
    private AlbumNumber albumNumber;

    @NotNull
    private Name Name;

    @NotNull
    private Surname Surname;

    @NotNull
    private StudentsRole studentsRole;

    @NotNull
    private FieldOfStudy fieldOfStudy;

    @NotNull
    private StudiesDegree studiesDegree;

    @NotNull
    private NoSemester numberOfSemester;

    @NotNull
    private List<SubjectChoice> subjectChoices;

    private AverageGrade averageGrade;

    private Speciality Speciality;

    public void deleteSubjectChoice(final SubjectId id) {
        subjectChoices.remove(id);
    }

    public void decreasePriority() {
       subjectChoices.forEach(s->s.setPriority(Priority.of(s.getPriority().getValue()-1)));
    }
}