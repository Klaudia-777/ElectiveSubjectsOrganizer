package org.agh.electer.core.infrastructure.dao;

import org.agh.electer.core.infrastructure.entities.AdminEntity;
import org.springframework.data.repository.CrudRepository;

public interface AdminDao extends CrudRepository<AdminEntity,String> {
}
