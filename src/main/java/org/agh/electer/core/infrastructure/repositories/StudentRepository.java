package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.springframework.stereotype.Repository;

public interface StudentRepository extends GenericRepository<Student, AlbumNumber>{
}
