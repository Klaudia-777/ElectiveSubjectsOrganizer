package org.agh.electer.core.domain.student;

import com.opencsv.bean.CsvBindByPosition;
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
import org.agh.electer.core.infrastructure.entities.TypeOfSemester;

import java.util.ArrayList;
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
    private TypeOfSemester typeOfSemester;

    @NotNull
    private StudiesDegree studiesDegree;

    @NotNull
    private NoSemester numberOfSemester;

    @NotNull
    private YearOfStudies year;

    @NotNull
    private int noQualifiedForSubjects;

    @NotNull
    @Builder.Default
    private List<SubjectChoice> subjectChoices = new ArrayList<>();

    private AverageGrade averageGrade;

    private Speciality Speciality;

    public void deleteSubjectChoice(final SubjectId id) {
        subjectChoices=subjectChoices.stream().filter(n->!n.getSubjectId().equals(id)).collect(Collectors.toList());
    }

    public void decreasePriority() {
        subjectChoices.forEach(s -> s.setPriority(Priority.of(s.getPriority().getValue() - 1)));
    }
    public void increaseNoQualiiedFoSubjects() {
        noQualifiedForSubjects+=1;
    }

    public SubjectChoice findSubjectChoiceBySubjectId(SubjectId subjectId) {
        return subjectChoices.stream().filter(sc -> sc.getSubjectId().equals(subjectId)).findFirst().orElse(null);
    }

    public Priority getSubjectChoicePriority(SubjectId subjectId) {
        return findSubjectChoiceBySubjectId(subjectId).getPriority();
    }

}