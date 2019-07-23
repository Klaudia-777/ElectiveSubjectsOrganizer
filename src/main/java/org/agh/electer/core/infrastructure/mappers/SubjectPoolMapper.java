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

import java.util.List;
import java.util.function.Function;
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

        return subjectEntity;
    }


    public static SubjectPool toDomain(SubjectPoolEntity s, final Function<String, List<SubjectChoice>> subjectChoiceSelector) {
        return SubjectPool.builder()
                .id(SubjectPoolId.of(s.getId()))
                .fieldOfStudy(s.getFieldOfStudy())
                .noSubjectsToAttend(NoSubjectsToAttend.of(s.getNumberOfSubjectsToAttend()))
                .electiveSubjects(s.getElectiveSubjects()
                        .stream()
                        .map(subjectEntity -> toDomain(subjectEntity, subjectChoiceSelector))
                        .collect(Collectors.toSet())
                ).build();
    }

    private static Subject toDomain(SubjectEntity subjectEntity, final Function<String, List<SubjectChoice>> subjectChoiceSelector) {
        return Subject.builder()
                .subjectId(SubjectId.of(subjectEntity.getId()))
                .name(Name.of(subjectEntity.getName()))
                .description(Description.of(subjectEntity.getDescription()))
                .numberOfPlaces(NoPlaces.of(subjectEntity.getNumberOfPlaces()))
                .tutor(Tutor.of(subjectEntity.getTutor()))
                .subjectChoices(subjectChoiceSelector.apply(subjectEntity.getId()))
                .build();
    }

}
