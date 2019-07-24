package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name="SUBJECT_TABLE")
public class SubjectEntity {
    @Id
    @Column(name= "SUBJECT_ID")
    private String id;

    @Column(name="SUBJECT_NAME")
    private String name;

    @Column(name="TUTOR")
    private String tutor;

    @Column(name="NO_PLACES")
    private int numberOfPlaces;

    @Column(name="DESCRIPTION")
    private String description;

    @ManyToOne(fetch= FetchType.EAGER, cascade = CascadeType.ALL)
    private SubjectPoolEntity subjectPool;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subject")
    @JoinColumn(name = "QUALIFIED_STUDENTS")
    private List<StudentEntity> qualifiedStudents;
}
