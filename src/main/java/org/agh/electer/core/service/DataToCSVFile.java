package org.agh.electer.core.service;

import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.subject.pool.NoSemester;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.dto.StudentDto;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DataToCSVFile {

    private SubjectPoolRepository subjectPoolRepository;
    private SubjectPool subjectPool;

    public DataToCSVFile(SubjectPoolRepository subjectPoolRepository) {
        this.subjectPoolRepository = subjectPoolRepository;
    }

    /***
     GET ONLY NAMES !!!
     */

//    private Set<String> getSubjectsForGivenSudentNotQualified(StudentDto studentDto) {
//        return subjectPool.getElectiveSubjects()
//                .stream()
//                .filter(n -> !n.getQualifiedStudents().contains(AlbumNumber.of(studentDto.getAlbumNumber())))
//                .map(u -> u.getSubjectName().getValue())
//                .collect(Collectors.toSet());
//    }
//
//    public static String toCSVData(StudentDto studentDto) {
//        String qualifiedOrNot = Stream.of();
//
//        return Stream.of(studentDto.getAlbumNumber(),
//                studentDto.getName(), studentDto.getSurname(),
//                String.valueOf(studentDto.getFieldOfStudy()),
//                String.valueOf(studentDto.getNumberOfSemester()),
//                String.valueOf(studentDto.getStudiesDegree()),
//                studentDto.getAverageGrade().toString().replace(".",",")).collect(Collectors.joining(";","","\n"));
//    }

}
