package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.dao.SubjectChoiceDao;
import org.agh.electer.core.infrastructure.dao.SubjectPoolDao;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;
import org.agh.electer.core.infrastructure.mappers.SubjectChoiceMapper;
import org.agh.electer.core.infrastructure.mappers.SubjectMapper;
import org.agh.electer.core.infrastructure.mappers.SubjectPoolMapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class SubjectPoolRepositoryImpl implements SubjectPoolRepository {
    private final SubjectPoolDao subjectPoolDao;

    private final SubjectChoiceDao subjectChoiceDao;

    public SubjectPoolRepositoryImpl(SubjectPoolDao subjectDao, SubjectChoiceDao subjectChoiceDao) {
        this.subjectPoolDao = subjectDao;
        this.subjectChoiceDao = subjectChoiceDao;
    }

    @Override
    public Optional<SubjectPool> findById(SubjectPoolId subjectPoolId) {
        return subjectPoolDao.findById(subjectPoolId.getValue())
                .map(e -> SubjectPoolMapper.toDomain(e, this::selectSubjectChoicesForSubject));
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
        return subjectPoolDao.findById(subjectPoolId.getValue())
                .map(SubjectPoolEntity::getElectiveSubjects)
                .get()
                .stream()
                .map(SubjectMapper::toDomain)
                .collect(Collectors.toSet());

    }

    //todo change to sql query
    private List<SubjectChoice> selectSubjectChoicesForSubject(String subjectId) {
        return StreamSupport.stream(subjectChoiceDao.findAll().spliterator(), false)
                .filter(s -> s.getSubjectId().equals(subjectId))
                .map(SubjectChoiceMapper::toDomain)
                .collect(Collectors.toList());
    }
}
