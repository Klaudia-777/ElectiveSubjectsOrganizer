//package org.agh.electer.core.infrastructure.mappers;
//
//import lombok.val;
//import org.agh.electer.core.domain.student.AlbumNumber;
//import org.agh.electer.core.domain.subject.choice.Priority;
//import org.agh.electer.core.domain.subject.choice.SubjectChoice;
//import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
//import org.agh.electer.core.infrastructure.entities.StudentEntity;
//import org.agh.electer.core.infrastructure.entities.SubjectChoiceEntity;
//import org.agh.electer.core.infrastructure.entities.SubjectEntity;
//
//public class SubjectChoiceMapper {
//    public static SubjectChoiceEntity toEntity(final SubjectChoice subjectChoice, StudentEntity studentEntity, SubjectEntity subjectEntity){
//        val entity = SubjectChoiceEntity.builder()
//                .id(subjectChoice.getId().getValue())
//                .priority(subjectChoice.getPriority().getValue())
//                .student(studentEntity)
//                .subject(subjectEntity)
//                .build();
//        return entity;
//    }
//
//}
