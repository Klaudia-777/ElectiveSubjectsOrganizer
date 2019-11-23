package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.EAGER;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "SUBJECT_TABLE")
public class SubjectEntity {
    @Id
    @Column(name = "SUBJECT_ID")
    private String id;

    @Column(name = "SUBJECT_NAME")
    private String name;

    @Column(name = "TUTOR")
    private String tutor;

    @Column(name = "NO_PLACES")
    private int numberOfPlaces;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne(fetch = EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_POOL")
    private SubjectPoolEntity subjectPool;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinColumn(subjectName = "QUALIFIED_STUDENTS")
    private List<SubjectChoiceEntity> subjectChoices;

    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    @CollectionTable(name = "gualifiedStudents", joinColumns = @JoinColumn(name = "subjectId"))
    @Column(name = "QUALIFIED_STUDENTS")
    private List<String> qualifiedStudents = new ArrayList<>();
}
