package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.domain.subject.pool.NoSubjectsToAttend;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.dto.SubjectPoolDto;

import java.util.stream.Collectors;

public class SubjectPoolDTOMapper {
    public static SubjectPool toDomain(final SubjectPoolDto dto) {
        val domain = SubjectPool.builder()
                .id(SubjectPoolId.of(dto.getId()))
                .fieldOfStudy(dto.getFieldOfStudy())
                .noSubjectsToAttend(NoSubjectsToAttend.of(dto.getNumberOfSubjectsToAttend()))
                .build();

        domain.setElectiveSubjects(dto.getElectiveSubjects()
                .stream()
                .map(SubjectDTOMapper::toDomain)
                .collect(Collectors.toSet()));

        return domain;
    }

    public static SubjectPoolDto toDto(final SubjectPool domain) {
        val dto = SubjectPoolDto.builder()
                .id(domain.getId().getValue())
                .fieldOfStudy(domain.getFieldOfStudy())
                .numberOfSubjectsToAttend(domain.getNoSubjectsToAttend().getValue())
                .build();

        dto.setElectiveSubjects(domain.getElectiveSubjects()
                .stream()
                .map(SubjectDTOMapper::toDto)
                .collect(Collectors.toSet()));

        return dto;
    }
}
