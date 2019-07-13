package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.springframework.stereotype.Repository;

import java.util.Set;
@Repository
public interface SubjectPoolRepository extends GenericRepository<SubjectPool, SubjectPoolId> {
    Set<Subject> getAllSubjectsFromPool(SubjectPoolId subjectPoolId);
}
