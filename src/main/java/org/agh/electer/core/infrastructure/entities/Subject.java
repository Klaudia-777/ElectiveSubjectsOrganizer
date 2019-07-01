package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name="SUBJECT_TABLE")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "SUBJECT_ID")
    private int id;

    @Column(name="SUBJECT_NAME")
    private String name;

    @Column(name="TUTOR")
    private String tutor;

    @Column(name="NO_PLACES")
    private int numberOfPlaces;

    @Column(name="DESCRIPTION")
    private String description;

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subjectSet")
    private SubjectSet subjectSet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_CHOICE_ID")
    private SubjectChoice subjectChoice;
}
