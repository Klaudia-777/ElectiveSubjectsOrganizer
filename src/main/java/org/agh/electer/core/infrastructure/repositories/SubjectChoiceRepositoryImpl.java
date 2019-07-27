package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.infrastructure.dao.SubjectChoiceDao;
import org.agh.electer.core.infrastructure.mappers.SubjectChoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubjectChoiceRepositoryImpl implements SubjectChoiceRepository {
    private final SubjectChoiceDao subjectChoiceDao;

    @Autowired
    public SubjectChoiceRepositoryImpl(SubjectChoiceDao subjectChoiceDao) {
        this.subjectChoiceDao = subjectChoiceDao;
    }

    @Override
    public SubjectChoice findById(SubjectChoiceId subjectChoiceId) {
        return subjectChoiceDao.findById(subjectChoiceId.getValue()).map(SubjectChoiceMapper::toDomain).orElse(null);
    }
}
