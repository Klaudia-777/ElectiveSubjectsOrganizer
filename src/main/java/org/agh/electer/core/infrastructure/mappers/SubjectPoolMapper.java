package org.agh.electer.core.infrastructure.mappers;

import lombok.experimental.UtilityClass;
import lombok.val;
import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.agh.electer.core.infrastructure.entities.SubjectEntity;
import org.agh.electer.core.infrastructure.entities.SubjectPoolEntity;

@UtilityClass
public class SubjectPoolMapper {
    public SubjectPoolEntity toEntity(final SubjectPool subjectPool) {
        val entity = SubjectPoolEntity.builder()
                .id(subjectPool.getId().getValue())
                .fieldOfStudy(subjectPool.getFieldOfStudy())
                .electiveSubjects(subjectPool.getElectiveSubjects()
                        .stream()
                        .map(S))
                .build();
    }
    private SubjectEntity toSubjectEntity(final Subject subject, final SubjectPoolEntity subjectPoolEntity){
        return SubjectEntity.builder()
                .id(subject.getSubjectId().getValue())
                .name(subject.getName().getValue())
                .numberOfPlaces(subject.getNumberOfPlaces().getValue())
                .tutor(subject.getTutor().getValue())
                .description(subject.getDescription().getValue())
                .subjectPool(subjectPoolEntity)
                .subjectChoices()
                .description().build()
    }
    private SubjectChoiceEntity toSubjectChoiceEntity(final SubjectChoice subjectChoice, ){
        return SubjectChoiceEntity.builder()
                .id(subjectChoice.getId().getValue())
                .priority(subjectChoice.getPriority().getValue())
                .student(subjectChoice.getStudent())
                .build()
    }

    public static SubjectPool toDomain(SubjectPoolEntity s) {
        return null;
    }
}
