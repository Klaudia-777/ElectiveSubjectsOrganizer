package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name="SUBJECT_POOL_TABLE")
public class SubjectPoolEntity {
    @Id
    @Column(name="SUBJECT_SET_ID")
    private String id;

    @Column(name = "FIELD_OF_STUDY")
    private FieldOfStudy fieldOfStudy;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subjectPool")
//    @JoinColumn(name = "SUBJECT_ID")
    private Set<SubjectEntity> electiveSubjects;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<StudentEntity> students;

    @Column(name = "NUMBER_OF_SUBJECTS_TO_ATTEND")
    private int numberOfSubjectsToAttend;

}
