package org.agh.electer.core.service;

import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CsvParser {
    private static final int ALBUM_NUMBER_COLUMN = 0;
    private static final int FIELD_OF_STUDY_COLUMN = 1;
    private static final int STUDIES_DEGREE_COLUMN = 2;
    private static final int N0_SEMESTER_COLUMN = 3;
    private static final int SURNAME_COLUMN = 4;
    private static final int NAME_COLUMN = 5;
    private static final int SPECIALITY_COLUMN = 6;
    private static final int STUDENTS_ROLE_COLUMN = 7;
    private static final int AVG_COLUMN = 8;

    public List<Student> parseStudentFile(MultipartFile multipartFile) throws IOException {
        InputStream is = multipartFile.getInputStream();
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(is));
        String row;
        List<Student> studentList = new ArrayList<>();
        csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            val student = Student.builder()
                    .albumNumber(AlbumNumber.of(data[ALBUM_NUMBER_COLUMN]))
                    .fieldOfStudy(FieldOfStudy.valueOf(data[FIELD_OF_STUDY_COLUMN]))
                    .studiesDegree(StudiesDegree.valueOf(data[STUDIES_DEGREE_COLUMN]))
                    .numberOfSemester(NoSemester.of(Integer.valueOf(data[N0_SEMESTER_COLUMN])))
                    .Surname(Surname.of(data[SURNAME_COLUMN]))
                    .Name(Name.of(data[NAME_COLUMN]))
//                    .Speciality(Speciality.of(data[SPECIALITY_COLUMN]))
//                    .studentsRole(StudentsRole.valueOf(data[STUDENTS_ROLE_COLUMN]))
//                    .averageGrade(AverageGrade.of(Double.valueOf(data[AVG_COLUMN])))
                    .build();
            System.out.println(student);
            studentList.add(student);
        }
        csvReader.close();
        return studentList;
    }
}
