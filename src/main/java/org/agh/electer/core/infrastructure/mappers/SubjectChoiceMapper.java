package org.agh.electer.core.infrastructure.mappers;

import lombok.val;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.infrastructure.entities.StudentEntity;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.agh.electer.core.infrastructure.entities.SubjectEntity;

public class SubjectChoiceMapper {
    public static SubjectChoiceEntity toEntity(StudentEntity studentEntity, SubjectEntity subjectEntity){
        val entity = SubjectChoiceEntity.builder()
                .id(SubjectChoiceId.of("0").toString())
                .priority(Priority.of(1).getValue())
                .student(studentEntity)
                .subject(subjectEntity)
                .build();
        return entity;
    }
}
