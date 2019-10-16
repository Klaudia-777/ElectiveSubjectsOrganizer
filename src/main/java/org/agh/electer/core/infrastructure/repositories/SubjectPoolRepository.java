package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import java.util.List;
import java.util.Set;
public interface SubjectPoolRepository extends GenericRepository<SubjectPool, SubjectPoolId> {
    SubjectPool findByFieldOfStudy(FieldOfStudy fieldOfStudy, NoSemester noSemester, StudiesDegree studiesDegree);

    Set<Subject> getAllSubjectsFromPool(SubjectPoolId subjectPoolId);
    List<SubjectChoice> selectSubjectChoicesForSubject(String subjectId);
}
