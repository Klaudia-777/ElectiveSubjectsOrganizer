package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.NoSubjectsToAttend;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.dto.StudentDto;
import org.agh.electer.core.dto.SubjectPoolDto;
import org.agh.electer.core.infrastructure.dao.StudentDao;
import org.agh.electer.core.infrastructure.entities.StudentEntity;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SubjectPoolDTOMapper {
    public static SubjectPool toDomain(final SubjectPoolDto dto) {
        val domain = SubjectPool.builder()
                .id(SubjectPoolId.of(dto.getId()))
                .date(dto.getDate())
                .fieldOfStudy(dto.getFieldOfStudy())
                .noSemester(NoSemester.of(dto.getNoSemester()))
                .studiesDegree(dto.getStudiesDegree())
                .noSubjectsToAttend(NoSubjectsToAttend.of(dto.getNumberOfSubjectsToAttend()))
                .build();

        domain.setStudents(dto.getStudents()
                .stream()
                .map(AlbumNumber::of)
                .collect(Collectors.toSet()));

        domain.setElectiveSubjects(dto.getElectiveSubjects()
                .stream()
                .map(SubjectDTOMapper::toDomain)
                .collect(Collectors.toSet()));

        return domain;
    }

    public static SubjectPoolDto toDto(final SubjectPool domain) {
        val dto = SubjectPoolDto.builder()
                .id(domain.getId().getValue())
                .date(domain.getDate())
                .fieldOfStudy(domain.getFieldOfStudy())
                .noSemester(domain.getNoSemester().getValue())
                .studiesDegree(domain.getStudiesDegree())
                .numberOfSubjectsToAttend(domain.getNoSubjectsToAttend().getValue())
                .build();

        dto.setStudents(domain.getStudents()
                .stream()
                .map(AlbumNumber::getValue)
                .collect(Collectors.toSet()));

        dto.setElectiveSubjects(domain.getElectiveSubjects()
                .stream()
                .map(SubjectDTOMapper::toDto)
                .collect(Collectors.toSet()));

        return dto;
    }
}
