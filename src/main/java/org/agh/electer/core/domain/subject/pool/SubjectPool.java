package org.agh.electer.core.domain.subject.pool;

import lombok.Builder;
import lombok.Data;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
public class SubjectPool {
    @NotNull
    private SubjectPoolId id;

    private FieldOfStudy fieldOfStudy;

    private NoSemester noSemester;

    private StudiesDegree studiesDegree;

    @NotNull
    private Set<Subject> electiveSubjects;

    private NoSubjectsToAttend noSubjectsToAttend;

    @NotNull
    private Set<AlbumNumber> students;
    private String date;

}
