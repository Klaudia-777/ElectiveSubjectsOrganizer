package org.agh.electer.core.infrastructure.dao;

import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentEntity;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
@Repository
public interface SubjectPoolDao extends CrudRepository<SubjectPoolEntity,String> {
    @Query("select distinct s from SUBJECT_POOL_TABLE s ")
    Set<SubjectPoolEntity> getAll();

    SubjectPoolEntity findByFieldOfStudyAndNumberOfSemesterAndStudiesDegree(FieldOfStudy fieldOfStudy,
                                                                              int numberOfSemester,
                                                                              StudiesDegree studiesDegree);
}

