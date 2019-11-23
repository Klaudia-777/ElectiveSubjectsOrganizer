package org.agh.electer.core.infrastructure.dao;

import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectChoiceDao extends CrudRepository<SubjectChoiceEntity,String> {
    List<SubjectChoiceEntity> findBySubjectId(String subjectId);
}
