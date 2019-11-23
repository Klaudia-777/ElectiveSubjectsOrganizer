package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.dao.StudentDao;
import org.agh.electer.core.infrastructure.dao.SubjectChoiceDao;
import org.agh.electer.core.infrastructure.dao.SubjectPoolDao;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;
import org.agh.electer.core.infrastructure.mappers.SubjectChoiceMapper;
import org.agh.electer.core.infrastructure.mappers.SubjectMapper;
import org.agh.electer.core.infrastructure.mappers.SubjectPoolMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Repository
public class SubjectPoolRepositoryImpl implements SubjectPoolRepository {
    private final SubjectPoolDao subjectPoolDao;
    private final StudentDao studentDao;
    private final SubjectChoiceDao subjectChoiceDao;

    public SubjectPoolRepositoryImpl(SubjectPoolDao subjectDao, StudentDao studentDao, SubjectChoiceDao subjectChoiceDao) {
        this.subjectPoolDao = subjectDao;
        this.studentDao = studentDao;
        this.subjectChoiceDao = subjectChoiceDao;
    }

    @Override
    public Optional<SubjectPool> findById(SubjectPoolId subjectPoolId) {
        return subjectPoolDao.findById(subjectPoolId.getValue())
                .map(n->SubjectPoolMapper.toDomain(n,subjectChoiceDao::findBySubjectId));
    }

    @Override
    public SubjectPool findByFieldOfStudy(FieldOfStudy fieldOfStudy, NoSemester noSemester, StudiesDegree studiesDegree) {
        return Optional.ofNullable(subjectPoolDao.findByFieldOfStudyAndNoSemesterAndStudiesDegree(
                fieldOfStudy,
                noSemester.getValue(),
                studiesDegree)).map(n->SubjectPoolMapper.toDomain(n,subjectChoiceDao::findBySubjectId))
                .orElse(null)
                ;
    }

    @Override
    public void deleteById(SubjectPoolId subjectPoolId) {
        subjectPoolDao.deleteById(subjectPoolId.getValue());
    }

    @Override
    public Set<SubjectPool> getAll() {
        return subjectPoolDao.getAll().stream().map(n->SubjectPoolMapper.toDomain(n,subjectChoiceDao::findBySubjectId)).collect(Collectors.toSet());
    }

    @Override
    public void save(SubjectPool subjectPool) {
        subjectPoolDao.save(SubjectPoolMapper.toEntity(subjectPool, studentDao::findById));
    }

    @Override
    public void update(SubjectPool subjectPool) {
        subjectPoolDao.save(SubjectPoolMapper.toEntity(subjectPool, studentDao::findById));
    }

    @Override
    public Set<Subject> getAllSubjectsFromPool(SubjectPoolId subjectPoolId) {
        return subjectPoolDao.findById(subjectPoolId.getValue())
                .map(SubjectPoolEntity::getElectiveSubjects)
                .get()
                .stream()
                .map(SubjectMapper::toDomain)
                .peek(s->s.setSubjectChoices(selectSubjectChoicesForSubject(s.getSubjectId().getValue()))) //<-----TODO :(
                .collect(Collectors.toSet());

    }

    //todo change to sql query
    public List<SubjectChoice> selectSubjectChoicesForSubject(String subjectId) {
        return StreamSupport.stream(subjectChoiceDao.findAll().spliterator(), false)
                .filter(s -> s.getSubjectId().equals(subjectId))
                .map(SubjectChoiceMapper::toDomain)
                .collect(Collectors.toList());
    }



}
