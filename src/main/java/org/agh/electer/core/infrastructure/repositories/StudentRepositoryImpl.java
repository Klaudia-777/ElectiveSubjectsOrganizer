package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.infrastructure.dao.StudentDao;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final StudentDao studentDao;

    @Autowired
    public StudentRepositoryImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Optional<Student> findById(AlbumNumber albumNumber) {
        return studentDao.findById(albumNumber.getValue())
                .map(StudentMapper::toDomain);
    }
    @Override
    public List<Student> findByFieldOfStudy(FieldOfStudy fieldOfStudy, NoSemester noSemester, StudiesDegree studiesDegree)
    {
        return studentDao.findByFieldOfStudyAndNumberOfSemesterAndStudiesDegree(
                fieldOfStudy,
                noSemester.getValue(),
                studiesDegree).stream()
                .map(StudentMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(AlbumNumber albumNumber) {
        studentDao.deleteById(albumNumber.getValue());
    }

    @Override
    public Set<Student> getAll() {
        return studentDao.getAll().stream().map(StudentMapper::toDomain).collect(Collectors.toSet());
    }

    @Override
    public void save(Student student) {
        studentDao.save(StudentMapper.toEntity(student));
    }

    @Override
    public void update(Student student) {
        studentDao.save(StudentMapper.toEntity(student));
    }
}
