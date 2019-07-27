package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.dto.SubjectChoiceDto;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;

public class SubjectChoiceDTOMapper {
    public static SubjectChoiceEntity toEntity(final SubjectChoiceDto dto) {
        val entity = SubjectChoiceEntity.builder()
                .id(dto.getId())
                .priority(dto.getPriority())
                .studentId(dto.getStudentId())
                .subjectId(dto.getSubjectId())
                .build();
        return entity;
    }

    public static SubjectChoiceDto toDto(final SubjectChoiceEntity entity) {
        return SubjectChoiceDto.builder()
                .id(entity.getSubjectId())
                .priority(entity.getPriority())
                .studentId(entity.getStudentId())
                .subjectId(entity.getSubjectId())
                .build();
    }
}
