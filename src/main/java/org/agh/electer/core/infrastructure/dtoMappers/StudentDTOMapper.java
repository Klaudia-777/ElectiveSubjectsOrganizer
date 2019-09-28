package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.dto.StudentDto;

import java.util.Optional;
import java.util.stream.Collectors;

public class StudentDTOMapper {

    public static Student toDomain(final StudentDto dto) {
        val domain = Student.builder()
                .albumNumber(AlbumNumber.of(dto.getAlbumNumber()))
                .Name(Name.of(dto.getName()))
                .Surname(Surname.of(dto.getSurname()))
                .studentsRole(dto.getStudentsRole())
                .studiesDegree(dto.getStudiesDegree())
                .averageGrade(Optional.ofNullable(dto.getAverageGrade()).map(AverageGrade::of).orElse(null))
                .numberOfSemester(NoSemester.of(dto.getNumberOfSemester()))
                .Speciality(Optional.ofNullable(dto.getSpeciality()).map(Speciality::of).orElse(null))
                .fieldOfStudy(dto.getFieldOfStudy())
                .typeOfSemester(dto.getTypeOfSemester())
                .year(YearOfStudies.of(dto.getYear()))
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
                .averageGrade(Optional.ofNullable(domain.getAverageGrade()).map(AverageGrade::getValue).orElse(null))
                .fieldOfStudy(domain.getFieldOfStudy())
                .numberOfSemester(domain.getNumberOfSemester().getValue())
                .year(domain.getYear().getValue())
                .Speciality(Optional.ofNullable(domain.getSpeciality()).map(Speciality::getValue).orElse(null))
                .studentsRole(domain.getStudentsRole())
                .studiesDegree(domain.getStudiesDegree())
                .typeOfSemester(domain.getTypeOfSemester())
                .build();
        dto.setSubjectChoices(domain.getSubjectChoices()
                .stream()
                .map(SubjectChoiceDTOMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
