package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.dto.SubjectDto;
import org.agh.electer.core.infrastructure.entities.SubjectEntity;

import java.util.stream.Collectors;

public class SubjectDTOMapper {
    public static SubjectEntity toEntity(final SubjectDto dto) {
        val entity = SubjectEntity.builder()
                .id(dto.getId())
                .name(dto.getName())
                .tutor(dto.getTutor())
                .description(dto.getDescription())
                .numberOfPlaces(dto.getNumberOfPlaces())
                .build();

        entity.setSubjectPool(SubjectPoolDTOMapper.toEntity(dto.getSubjectPool()));

        entity.setQualifiedStudents(dto.getQualifiedStudents()
                .stream()
                .map(StudentDTOMapper::toEntity)
                .collect(Collectors.toList()));

        return entity;
    }

    public static SubjectDto toDto(final SubjectEntity entity) {
        val dto = SubjectDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .tutor(entity.getTutor())
                .description(entity.getDescription())
                .numberOfPlaces(entity.getNumberOfPlaces())
                .build();

        dto.setSubjectPool(SubjectPoolDTOMapper.toDto(entity.getSubjectPool()));

        dto.setQualifiedStudents(entity.getQualifiedStudents()
                .stream()
                .map(StudentDTOMapper::toDto)
                .collect(Collectors.toList()));

        return dto;
    }
}
