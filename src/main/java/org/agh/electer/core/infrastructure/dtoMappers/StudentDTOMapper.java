package org.agh.electer.core.infrastructure.dtoMappers;

import lombok.val;
import org.agh.electer.core.dto.StudentDto;
import org.agh.electer.core.infrastructure.entities.StudentEntity;

import java.util.stream.Collectors;

public class StudentDTOMapper {

    public static StudentEntity toEntity(final StudentDto dto) {
        val entity = StudentEntity.builder()
                .albumNumber(dto.getAlbumNumber())
                .Name(dto.getName())
                .Surname(dto.getSurname())
                .studentsRole(dto.getStudentsRole())
                .studiesDegree(dto.getStudiesDegree())
                .averageGrade(dto.getAverageGrade())
                .numberOfSemester(dto.getNumberOfSemester())
                .Speciality(dto.getSpeciality())
                .fieldOfStudy(dto.getFieldOfStudy())
                .build();
        entity.setSubjectChoices(dto.getSubjectChoices()
                .stream()
                .map(SubjectChoiceDTOMapper::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }

    public static StudentDto toDto(final StudentEntity entity) {

        val dto = StudentDto.builder()
                .albumNumber(entity.getAlbumNumber())
                .Name(entity.getName())
                .Surname(entity.getSurname())
                .averageGrade(entity.getAverageGrade())
                .fieldOfStudy(entity.getFieldOfStudy())
                .numberOfSemester(entity.getNumberOfSemester())
                .Speciality(entity.getSpeciality())
                .studentsRole(entity.getStudentsRole())
                .studiesDegree(entity.getStudiesDegree())
                .build();
        dto.setSubjectChoices(entity.getSubjectChoices()
                .stream()
                .map(SubjectChoiceDTOMapper::toDto)
                .collect(Collectors.toList()));
        return dto;
    }
}
