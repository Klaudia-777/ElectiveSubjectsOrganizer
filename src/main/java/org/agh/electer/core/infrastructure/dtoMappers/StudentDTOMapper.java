package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.dto.StudentDto;

import java.util.stream.Collectors;

public class StudentDTOMapper {

    public static Student toDomain(final StudentDto dto) {
        val domain = Student.builder()
                .albumNumber(AlbumNumber.of(dto.getAlbumNumber()))
                .Name(Name.of(dto.getName()))
                .Surname(Surname.of(dto.getSurname()))
                .studentsRole(dto.getStudentsRole())
                .studiesDegree(dto.getStudiesDegree())
                .averageGrade(AverageGrade.of(dto.getAverageGrade()))
                .numberOfSemester(NoSemester.of(dto.getNumberOfSemester()))
                .Speciality(Speciality.of(dto.getSpeciality()))
                .fieldOfStudy(dto.getFieldOfStudy())
                .build();
        domain.setSubjectChoices(dto.getSubjectChoices()
                .stream()
                .map(SubjectChoiceDTOMapper::toDomain)
                .collect(Collectors.toList()));
        return domain;
    }

    public static StudentDto toDto(final Student domain) {

        val dto = StudentDto.builder()
                .albumNumber(domain.getAlbumNumber().getValue())
                .Name(domain.getName().getValue())
                .Surname(domain.getSurname().getValue())
                .averageGrade(domain.getAverageGrade().getValue())
                .fieldOfStudy(domain.getFieldOfStudy())
                .numberOfSemester(domain.getNumberOfSemester().getValue())
                .Speciality(domain.getSpeciality().getValue())
                .studentsRole(domain.getStudentsRole())
                .studiesDegree(domain.getStudiesDegree())
                .build();
        dto.setSubjectChoices(domain.getSubjectChoices()
                .stream()
                .map(SubjectChoiceDTOMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
