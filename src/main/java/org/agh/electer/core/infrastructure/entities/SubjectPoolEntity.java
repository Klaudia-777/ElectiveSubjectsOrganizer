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

    @Column(name="DATE")
    private String date;

    @Column(name = "FIELD_OF_STUDY")
    @Enumerated(value = EnumType.STRING)
    private FieldOfStudy fieldOfStudy;

    @Column(name = "NO_SEMESTER")
    private Integer noSemester;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "STUDIES_DEGREE")
    private StudiesDegree studiesDegree;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "subjectPool")
//    @JoinColumn(name = "SUBJECT_ID")
    private Set<SubjectEntity> electiveSubjects;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<StudentEntity> students;

    @Column(name = "NUMBER_OF_SUBJECTS_TO_ATTEND")
    private Integer numberOfSubjectsToAttend;

}
