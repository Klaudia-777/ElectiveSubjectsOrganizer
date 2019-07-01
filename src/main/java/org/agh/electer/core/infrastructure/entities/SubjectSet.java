package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name="SUBJECT_SET_TABLE")
public class SubjectSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="SUBJECT_SET_ID")
    private int id;

    @Column(name = "FIELD_OF_STUDY")
    private FieldOfStudy fieldOfStudy;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SUBJECT_ID")
    private Set<Subject> subjectSet;
}
