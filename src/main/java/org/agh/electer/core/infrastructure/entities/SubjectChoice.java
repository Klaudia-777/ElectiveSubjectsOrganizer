package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity(name = "SUBJECT_CHOICE_TABLE")
public class SubjectChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SUBJECT_CHOICE_ID")
    private int id;

    @Column(name = "PRIORITY")
    private int priority;


    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "subjectChoice")
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "STUDENT_ID")
    private Student student;
}
