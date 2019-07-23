package org.agh.electer.core.infrastructure.dao;

import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectChoiceDao extends CrudRepository<SubjectChoiceEntity,String> {
}
