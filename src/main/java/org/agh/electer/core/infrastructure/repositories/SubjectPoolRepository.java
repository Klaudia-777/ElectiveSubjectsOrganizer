package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
public interface SubjectPoolRepository extends GenericRepository<SubjectPool, SubjectPoolId> {
    Set<Subject> getAllSubjectsFromPool(SubjectPoolId subjectPoolId);
    List<SubjectChoice> selectSubjectChoicesForSubject(String subjectId);
}
