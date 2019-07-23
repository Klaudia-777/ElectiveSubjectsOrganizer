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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private SubjectEntity subject;

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "STUDENT_ID")
//    private StudentEntity student;

    private String studentId;
}
