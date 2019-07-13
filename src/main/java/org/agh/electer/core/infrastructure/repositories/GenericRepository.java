package org.agh.electer.core.infrastructure.repositories;

import java.util.Optional;
import java.util.Set;

public interface GenericRepository<DomainObject, Id> {
    Optional<DomainObject> findById(Id id);
    void deleteById(Id id);
    Set<DomainObject> getAll();
    void save(DomainObject domainObject);
    void update(DomainObject domainObject);
}
