package org.agh.electer.core.infrastructure.mappers;

import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.SubjectId;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.QualifiedOrNot;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.infrastructure.entities.StudentEntity;
import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
import org.agh.electer.core.infrastructure.entities.SubjectEntity;

public class SubjectChoiceMapper {
    public static SubjectChoiceEntity toEntity(final SubjectChoice subjectChoice,final StudentEntity studentEntity){
        val entity = SubjectChoiceEntity.builder()
                .id(subjectChoice.getId().getValue())
                .priority(subjectChoice.getPriority().getValue())
                .studentId(subjectChoice.getId().getValue())
                .subjectId(studentEntity.getAlbumNumber())
                .qualifiedOfNot(subjectChoice.getQualifiedOrNot().isValue())
                .build();
        return entity;
    }



    public static SubjectChoice toDomain(final SubjectChoiceEntity entity) {
        return SubjectChoice.builder()
                .id(SubjectChoiceId.of(entity.getId()))
                .priority(Priority.of(entity.getPriority()))
                .subjectId(SubjectId.of(entity.getSubjectId()))
                .studentId(AlbumNumber.of(entity.getStudentId()))
                .qualifiedOrNot(QualifiedOrNot.of(entity.isQualifiedOfNot()))
                .build();
    }

}
