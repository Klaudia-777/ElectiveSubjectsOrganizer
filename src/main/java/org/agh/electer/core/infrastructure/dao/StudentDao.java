package org.agh.electer.core.infrastructure.dao;

import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentEntity;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface StudentDao extends CrudRepository<StudentEntity, String> {
    @Query("select distinct s from STUDENTS_TABLE s ")
    Set<StudentEntity> getAll();
    List<StudentEntity> findByFieldOfStudyAndNumberOfSemesterAndStudiesDegree(FieldOfStudy fieldOfStudy,
                                                                              int numberOfSemester,
                                                                        StudiesDegree studiesDegree);
}
