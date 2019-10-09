package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;

import java.util.List;

public interface StudentRepository extends GenericRepository<Student, AlbumNumber>{
    List<Student> findByFieldOfStudy(FieldOfStudy fieldOfStudy, NoSemester noSemester, StudiesDegree studiesDegree);

}
