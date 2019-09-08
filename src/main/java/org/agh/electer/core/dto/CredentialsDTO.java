package org.agh.electer.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;


@Getter
@Setter
@Data
public class CredentialsDTO {
    private String albumNumber;
    private String name;
    private String surname;
    private FieldOfStudy fieldOfStudy;
    private StudiesDegree studiesDegree;
    private String speciality;
    private int numberOfSemester;
    private double averageGrade;
}
