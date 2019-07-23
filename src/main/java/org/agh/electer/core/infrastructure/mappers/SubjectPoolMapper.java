package org.agh.electer.core.infrastructure.mappers;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.*;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.domain.subject.pool.NoSubjectsToAttend;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.entities.StudentEntity;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.agh.electer.core.infrastructure.entities.SubjectEntity;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;

import java.util.stream.Collectors;

@UtilityClass
public class SubjectPoolMapper {
    public SubjectPoolEntity toEntity(final SubjectPool subjectPool) {
        val entity = SubjectPoolEntity.builder()
                .id(subjectPool.getId().getValue())
                .fieldOfStudy(subjectPool.getFieldOfStudy())
                .numberOfSubjectsToAttend(subjectPool.getNoSubjectsToAttend().getValue())
                .build();
        val subjectEntities = subjectPool.getElectiveSubjects().stream().map(s -> toSubjectEntity(s, entity)).collect(Collectors.toSet());
        entity.setElectiveSubjects(subjectEntities);
        return entity;
    }

    private SubjectEntity toSubjectEntity(final Subject subject, final SubjectPoolEntity subjectPoolEntity) {
        val subjectEntity = SubjectEntity.builder()
                .id(subject.getSubjectId().getValue())
                .name(subject.getName().getValue())
                .numberOfPlaces(subject.getNumberOfPlaces().getValue())
                .tutor(subject.getTutor().getValue())
                .description(subject.getDescription().getValue())
                .subjectPool(subjectPoolEntity)
                .build();

        val subjectChoicesEntity = subject.getSubjectChoices()
                .stream()
                .map(s -> toSubjectChoiceEntity(s,  subjectEntity))
                .collect(Collectors.toList());
        subjectEntity.setSubjectChoices(subjectChoicesEntity);
        return subjectEntity;
    }


    private SubjectChoiceEntity toSubjectChoiceEntity(final SubjectChoice subjectChoice,
                                                      final SubjectEntity subjectEntity) {

        return SubjectChoiceEntity.builder()
                .id(subjectChoice.getId().getValue())
                .priority(subjectChoice.getPriority().getValue())
                .studentId(subjectChoice.getStudent().getValue())
                .subject(subjectEntity)
                .build();
    }

    public static SubjectPool toDomain(SubjectPoolEntity s) {
        return SubjectPool.builder()
                .id(SubjectPoolId.of(s.getId()))
                .fieldOfStudy(s.getFieldOfStudy())
                .noSubjectsToAttend(NoSubjectsToAttend.of(s.getNumberOfSubjectsToAttend()))
                .electiveSubjects(s.getElectiveSubjects()
                        .stream()
                        .map(SubjectPoolMapper::toDomain)
                        .collect(Collectors.toSet())
                ).build();
    }

    private static Subject toDomain(SubjectEntity subjectEntity) {
        return Subject.builder()
                .subjectId(SubjectId.of(subjectEntity.getId()))
                .name(Name.of(subjectEntity.getName()))
                .description(Description.of(subjectEntity.getDescription()))
                .numberOfPlaces(NoPlaces.of(subjectEntity.getNumberOfPlaces()))
                .tutor(Tutor.of(subjectEntity.getTutor()))
                .subjectChoices(subjectEntity.getSubjectChoices()
                        .stream()
                        .map(SubjectPoolMapper::toDomain)
                        .collect(Collectors.toList()))
                .build();
    }

    private static SubjectChoice toDomain(SubjectChoiceEntity subjectChoiceEntity) {
        return SubjectChoice.builder()
                .id(SubjectChoiceId.of(subjectChoiceEntity.getId()))
                .priority(Priority.of(subjectChoiceEntity.getPriority()))
                .student(AlbumNumber.of(subjectChoiceEntity.getStudentId()))
                .build();
    }

}
