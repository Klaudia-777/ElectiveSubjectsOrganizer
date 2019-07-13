package org.agh.electer.core.service;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.Subject;

import java.util.List;

@Value
@AllArgsConstructor(staticName = "of")
public class ComputingUnit {
    private Subject subject;
    private List<Student> students;
}
