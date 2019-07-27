package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.domain.subject.*;
import org.agh.electer.core.dto.SubjectDto;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubjectDTOMapper {
    public static Subject toDomain(final SubjectDto dto) {
        val domain = Subject.builder()
                .subjectId(SubjectId.of(dto.getId()))
                .name(Name.of(dto.getName()))
                .tutor(Tutor.of(dto.getTutor()))
                .description(Description.of(dto.getDescription()))
                .numberOfPlaces(NoPlaces.of(dto.getNumberOfPlaces()))
                .build();

        domain.setQualifiedStudents(mapList(dto.getQualifiedStudents(),StudentDTOMapper::toDomain));
        domain.setSubjectChoices(mapList(dto.getSubjectChoices(),SubjectChoiceDTOMapper::toDomain));

        return domain;
    }

    public static SubjectDto toDto(final Subject domain) {
        val dto = SubjectDto.builder()
                .id(domain.getSubjectId().getValue())
                .name(domain.getName().getValue())
                .tutor(domain.getTutor().getValue())
                .description(domain.getDescription().getValue())
                .numberOfPlaces(domain.getNumberOfPlaces().getValue())
                .build();

        dto.setQualifiedStudents(mapList(domain.getQualifiedStudents(),StudentDTOMapper::toDto));
        dto.setSubjectChoices(mapList(domain.getSubjectChoices(),SubjectChoiceDTOMapper::toDto));

        return dto;
    }

    private static <FROM, TO> List<TO> mapList(List<FROM> list, Function<FROM, TO> mapper) {
        return list.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }
}
