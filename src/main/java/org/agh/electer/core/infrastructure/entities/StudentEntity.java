package org.agh.electer.core.infrastructure.entities;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity(name = "STUDENTS_TABLE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class StudentEntity {

    @Id
    @Column(name="NO_ALBUM")
    private String albumNumber;

    @Column(name = "NAME")
    private String Name;

    @Column (name="SURNAME")
    private String Surname;

    @Column(name="ROLE")
    private StudentsRole studentsRole;

    @Column (name= "FIELD_OF_STUDY")
    private FieldOfStudy fieldOfStudy;

    @Column (name= "DEGREE")
    private StudiesDegree studiesDegree;

    @Column (name= "SPECIALITY")
    private String Speciality;

    @Column (name= "NO_SEMESTER")
    private int numberOfSemester;

    @Column (name= "AVERAGE_GRADE")
    private double averageGrade;

    @OneToMany(fetch= FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "student")
    private List<SubjectChoiceEntity> subjectChoices;

}
