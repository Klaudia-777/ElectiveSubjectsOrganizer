package org.agh.electer.core.infrastructure.mappers;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.*;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.NoSubjectsToAttend;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.entities.StudentEntity;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.agh.electer.core.infrastructure.entities.SubjectEntity;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class SubjectPoolMapper {
    public SubjectPoolEntity toEntity(final SubjectPool subjectPool, Function<String, Optional<StudentEntity>> findById) {
        val entity = SubjectPoolEntity.builder()
                .id(subjectPool.getId().getValue())
                .fieldOfStudy(Optional.ofNullable(subjectPool.getFieldOfStudy()).orElse(null))
                .numberOfSubjectsToAttend(Optional.ofNullable(subjectPool.getNoSubjectsToAttend()).map(NoSubjectsToAttend::getValue).orElse(null))
                .noSemester(Optional.ofNullable(subjectPool.getNoSemester()).map(NoSemester::getValue).orElse(null))
                .studiesDegree(Optional.ofNullable(subjectPool.getStudiesDegree()).orElse(null))
                .students(subjectPool.getStudents()
                        .stream()
                        .map(s -> findById.apply(s.getValue()).orElse(null))
                        .collect(Collectors.toSet()))
                .build();
        val subjectEntities = subjectPool.getElectiveSubjects().stream().map(s -> toSubjectEntity(s, entity)).collect(Collectors.toSet());
        entity.setElectiveSubjects(subjectEntities);
        return entity;
    }

    private SubjectEntity toSubjectEntity(final Subject subject, final SubjectPoolEntity subjectPoolEntity) {
        val subjectEntity = SubjectEntity.builder()
                .id(subject.getSubjectId().getValue())
                .name(subject.getSubjectName().getValue())
                .numberOfPlaces(subject.getNumberOfPlaces().getValue())
                .tutor(subject.getTutor().getValue())
                .description(Optional.ofNullable(subject.getDescription()).map(Description::getValue).orElse(null))
                .subjectPool(Optional.ofNullable(subjectPoolEntity).orElse(null))
                .qualifiedStudents(subject.getQualifiedStudents().stream().map(AlbumNumber::getValue).collect(Collectors.toList()))
                .build();

        return subjectEntity;
    }


    public static SubjectPool toDomain(SubjectPoolEntity s, Function<String, List<SubjectChoiceEntity>> getSubjectChoice) {
        return SubjectPool.builder()
                .id(SubjectPoolId.of(s.getId()))
                .fieldOfStudy(s.getFieldOfStudy())
                .noSubjectsToAttend(NoSubjectsToAttend.of(s.getNumberOfSubjectsToAttend()))
                .noSemester(NoSemester.of(s.getNoSemester()))
                .studiesDegree(s.getStudiesDegree())
                .students(s.getStudents().stream().map(StudentEntity::getAlbumNumber).map(AlbumNumber::of).collect(Collectors.toSet()))
                .electiveSubjects(s.getElectiveSubjects()
                        .stream()
                        .map(n -> toDomain(n, getSubjectChoice))
                        .collect(Collectors.toSet())
                ).build();
    }

    private static Subject toDomain(SubjectEntity subjectEntity, Function<String, List<SubjectChoiceEntity>> getSubjectChoice) {
        return Subject.builder()
                .subjectId(SubjectId.of(subjectEntity.getId()))
                .subjectName(SubjectName.of(subjectEntity.getName()))
                .description(Description.of(subjectEntity.getDescription()))
                .numberOfPlaces(NoPlaces.of(subjectEntity.getNumberOfPlaces()))
                .tutor(Tutor.of(subjectEntity.getTutor()))
                .subjectChoices(getSubjectChoice.apply(subjectEntity.getId())
                        .stream()
                        .map(SubjectChoiceMapper::toDomain)
                        .collect(Collectors.toList()))
                .qualifiedStudents(subjectEntity.getQualifiedStudents()
                        .stream()
                        .map(AlbumNumber::of)
                        .collect(Collectors.toList()))
                .build();
    }

}
