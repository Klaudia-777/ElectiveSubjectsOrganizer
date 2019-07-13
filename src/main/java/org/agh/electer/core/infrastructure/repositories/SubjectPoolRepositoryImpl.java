package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.dao.SubjectPoolDao;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;
import org.agh.electer.core.infrastructure.mappers.SubjectMapper;
import org.agh.electer.core.infrastructure.mappers.SubjectPoolMapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SubjectPoolRepositoryImpl implements SubjectPoolRepository {
    private final SubjectPoolDao subjectPoolDao;

    public SubjectPoolRepositoryImpl(SubjectPoolDao subjectDao) {
        this.subjectPoolDao = subjectDao;
    }

    @Override
    public Optional<SubjectPool> findById(SubjectPoolId subjectPoolId) {
        return subjectPoolDao.findById(subjectPoolId.getValue())
                .map(e -> SubjectPoolMapper.toDomain(e));
    }

    @Override
    public void deleteById(SubjectPoolId subjectPoolId) {
        subjectPoolDao.deleteById(subjectPoolId.getValue());
    }

    @Override
    public Set<SubjectPool> getAll() {
        return subjectPoolDao.getAll();
    }

    @Override
    public void save(SubjectPool subjectPool) {
        subjectPoolDao.save(SubjectPoolMapper.toEntity(subjectPool));
    }

    @Override
    public void update(SubjectPool subjectPool) {
        subjectPoolDao.save(SubjectPoolMapper.toEntity(subjectPool));

    }

    @Override
    public Set<Subject> getAllSubjectsFromPool(SubjectPoolId subjectPoolId) {
        return  subjectPoolDao.findById(subjectPoolId.getValue())
                .map(SubjectPoolEntity::getElectiveSubjects)
                .get()
                .stream()
                .map(SubjectMapper::toDomain)
                .collect(Collectors.toSet());

    }
}
