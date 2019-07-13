package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.infrastructure.dao.StudentDao;
import org.agh.electer.core.infrastructure.mappers.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

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
                .map(e -> StudentMapper.toDomain(e));
    }

    @Override
    public void deleteById(AlbumNumber albumNumber) {
        studentDao.deleteById(albumNumber.getValue());
    }

    @Override
    public Set<Student> getAll() {
        return studentDao.getAll();
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
