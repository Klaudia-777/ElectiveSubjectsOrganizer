package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.dto.SubjectPoolDto;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;

import java.util.stream.Collectors;

public class SubjectPoolDTOMapper {
    public static SubjectPoolEntity toEntity(final SubjectPoolDto dto) {
        val entity = SubjectPoolEntity.builder()
                .id(dto.getId())
                .fieldOfStudy(dto.getFieldOfStudy())
                .numberOfSubjectsToAttend(dto.getNumberOfSubjectsToAttend())
                .build();

        entity.setElectiveSubjects(dto.getElectiveSubjects()
                .stream()
                .map(SubjectDTOMapper::toEntity)
                .collect(Collectors.toSet()));

        return entity;
    }

    public static SubjectPoolDto toDto(final SubjectPoolEntity entity) {
        val dto = SubjectPoolDto.builder()
                .id(entity.getId())
                .fieldOfStudy(entity.getFieldOfStudy())
                .numberOfSubjectsToAttend(entity.getNumberOfSubjectsToAttend())
                .build();

        dto.setElectiveSubjects(entity.getElectiveSubjects()
                .stream()
                .map(SubjectDTOMapper::toDto)
                .collect(Collectors.toSet()));

        return dto;
    }
}
