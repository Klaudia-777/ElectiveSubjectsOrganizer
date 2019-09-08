package org.agh.electer.core.infrastructure.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "SUBJECT_CHOICE_TABLE")
public class SubjectChoiceEntity {
    @Id
    @Column(name = "SUBJECT_CHOICE_ID")
    private String id;

    @Column(name = "PRIORITY")
    private int priority;

    private String studentId;

    private String subjectId;

    private boolean qualifiedOfNot;
}

