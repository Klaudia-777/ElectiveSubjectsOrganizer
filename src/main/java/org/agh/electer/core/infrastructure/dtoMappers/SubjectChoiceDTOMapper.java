package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.SubjectId;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.QualifiedOrNot;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.dto.SubjectChoiceDto;

public class SubjectChoiceDTOMapper {
    public static SubjectChoice toDomain(final SubjectChoiceDto dto) {
        val domain = SubjectChoice.builder()
                .id(SubjectChoiceId.of(dto.getId()))
                .priority(Priority.of(dto.getPriority()))
                .studentId(AlbumNumber.of(dto.getStudentId()))
                .subjectId(SubjectId.of(dto.getSubjectId()))
                .qualifiedOrNot(QualifiedOrNot.of(dto.isQualifiedOrNot()))
                .build();
        return domain;
    }

    public static SubjectChoiceDto toDto(final SubjectChoice domain) {
        return SubjectChoiceDto.builder()
                .id(domain.getSubjectId().getValue())
                .priority(domain.getPriority().getValue())
                .studentId(domain.getStudentId().getValue())
                .subjectId(domain.getSubjectId().getValue())
                .qualifiedOrNot(domain.getQualifiedOrNot().isValue())
                .build();
    }
}
