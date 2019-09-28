package org.agh.electer.core.service;

import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.entities.TypeOfSemester;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvParser {
    private static final int SURNAME_COLUMN = 0;
    private static final int NAME_COLUMN = 1;
    private static final int ALBUM_NUMBER_COLUMN = 2;
    private static final int FIELD_OF_STUDY_COLUMN = 3;
    private static final int STUDIES_DEGREE_COLUMN = 4;
    private static final int YEAR_COLUMN = 5;
    private static final int N0_SEMESTER_COLUMN = 6;
    private static final int TYPE_COLUMN = 7;
    private static final int AVG_COLUMN = 8;
    private static final int SPECIALITY_COLUMN = 9;
    private static final int STUDENTS_ROLE_COLUMN = 10;


    public List<Student> parseStudentFile(MultipartFile multipartFile, StudentRepository studentRepository) throws IOException {
        InputStream is = multipartFile.getInputStream();
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(is));
        String row;
        List<Student> studentList = new ArrayList<>();
        csvReader.readLine();
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            val student = Student.builder().build();
            if (studentRepository.findById(AlbumNumber.of(data[ALBUM_NUMBER_COLUMN])).orElse(null) == null) {
                student.setAlbumNumber(AlbumNumber.of(data[ALBUM_NUMBER_COLUMN]));
                student.setFieldOfStudy(FieldOfStudy.valueOf(data[FIELD_OF_STUDY_COLUMN]));
                student.setStudiesDegree(StudiesDegree.valueOf(data[STUDIES_DEGREE_COLUMN].replace(' ','_')));
                student.setNumberOfSemester(NoSemester.of(Integer.valueOf(data[N0_SEMESTER_COLUMN])));
                student.setSurname(Surname.of(data[SURNAME_COLUMN]));
                student.setName(Name.of(data[NAME_COLUMN]));
                student.setSpeciality(Speciality.of(data[SPECIALITY_COLUMN]));
                student.setAverageGrade(AverageGrade.of(Double.valueOf(data[AVG_COLUMN].replace(',','.'))));
                student.setYear(YearOfStudies.of(Integer.valueOf(data[YEAR_COLUMN])));
                student.setTypeOfSemester(TypeOfSemester.valueOf(data[TYPE_COLUMN]));


                if (Integer.valueOf(data[STUDENTS_ROLE_COLUMN]) == 1) {
                    student.setStudentsRole(StudentsRole.YearGroupRepresentative);
                } else {
                    student.setStudentsRole(StudentsRole.RegularStudent);
                }
                System.out.println(student);
                studentList.add(student);
            }
            System.out.println(student);
        }
        csvReader.close();
        return studentList;
    }
}
