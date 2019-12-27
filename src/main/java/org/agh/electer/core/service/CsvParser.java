package org.agh.electer.core.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import lombok.val;
import org.agh.electer.core.domain.student.*;
import org.agh.electer.core.domain.subject.*;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.pool.NoSubjectsToAttend;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.dto.StudentDto;
import org.agh.electer.core.infrastructure.dtoMappers.StudentDTOMapper;
import org.agh.electer.core.infrastructure.dtoMappers.SubjectChoiceDTOMapper;
import org.agh.electer.core.infrastructure.entities.FieldOfStudy;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.entities.StudiesDegree;
import org.agh.electer.core.infrastructure.entities.TypeOfSemester;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CsvParser {
    private DataToCSVFile dataToCSVFile;
    private static Map<String, String> subjectsMap = new HashMap<>();

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


    private static final int SUBJECT_POOL_FIELD_OF_STUDY_COLUMN = 0;
    private static final int GRADE_COLUMN = 1;
    private static final int NO_SEMESTER_COLUMN = 2;
    private static final int SUBJECT_NAME_COLUMN = 3;
    private static final int LECTURER_COLUMN = 4;
    private static final int NO_PLACES_COLUMN = 5;

    public CsvParser(DataToCSVFile dataToCSVFile) {
        this.dataToCSVFile = dataToCSVFile;
    }

    public List<Student> parseStudentFile(MultipartFile multipartFile,
                                          StudentRepository studentRepository) throws IOException {
        InputStream is = multipartFile.getInputStream();
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(is,"Windows-1250"));
        String row;

        List<Student> studentList = new ArrayList<>();
        String firstLine = csvReader.readLine();
//        if(firstLine.equals("Nazwisko;Imi«;Nr albumu;Kierunek studi—w;Typ studi—w;Rok studi—w;Nr semestru;Typ semestru;ĺrednia za 2 poprzednie semestry;SpecjalnoćŤ;Starosta")){
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(";");
            val student = Student.builder().build();
            if (studentRepository.findById(AlbumNumber.of(data[ALBUM_NUMBER_COLUMN])).orElse(null) == null) {
                student.setAlbumNumber(AlbumNumber.of(data[ALBUM_NUMBER_COLUMN]));
                student.setFieldOfStudy(FieldOfStudy.valueOf(data[FIELD_OF_STUDY_COLUMN]));
                student.setStudiesDegree(StudiesDegree.valueOf(data[STUDIES_DEGREE_COLUMN].replace(' ', '_').toLowerCase()));
                student.setNumberOfSemester(NoSemester.of(Integer.valueOf(data[N0_SEMESTER_COLUMN])));
                student.setSurname(Surname.of(data[SURNAME_COLUMN]));
                student.setName(Name.of(data[NAME_COLUMN]));
                student.setSpeciality(Speciality.of(data[SPECIALITY_COLUMN]));
                student.setAverageGrade(AverageGrade.of(Double.valueOf(data[AVG_COLUMN].replace(',', '.'))));
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

//    }
//        else
//            return null;
    }

    public SubjectPool parseSubjectFile(MultipartFile multipartFile,
                                        SubjectPoolRepository subjectPoolRepository,
                                        StudentRepository studentRepository) throws IOException {

        InputStream is = multipartFile.getInputStream();
        BufferedReader csvReader = new BufferedReader(new InputStreamReader(is,"Windows-1250"));
        String row;
        SubjectPool subjectPool = SubjectPool.builder().build();
        Set<Subject> subjectSet = new HashSet<>();
        String firstLine = csvReader.readLine();

        if (firstLine.equals("Kierunek;Stopień;Semestr;Nazwa przedmiotu;Prowadzący;Limit")) {
            subjectPool.setId(SubjectPoolId.of(UUID.randomUUID().toString()));
            subjectPool.setStudents(new HashSet<>());
            subjectPool.setElectiveSubjects(new HashSet<>());
            subjectPoolRepository.save(subjectPool);

            while ((row = csvReader.readLine()) != null) {
                String[] data = row.split(";");
                val subject = Subject.builder()
                        .subjectId(SubjectId.of((UUID.randomUUID().toString())))
                        .subjectName(SubjectName.of(data[SUBJECT_NAME_COLUMN]))
                        .tutor(Tutor.of(data[LECTURER_COLUMN]))
                        .numberOfPlaces(NoPlaces.of(Integer.valueOf(data[NO_PLACES_COLUMN])))
                        .build();
                subjectSet.add(subject);

                setStudentsForSubjectPool(studentRepository, subjectPool, data);

            }
            subjectPool.setElectiveSubjects(subjectSet);
            subjectPool.setNoSubjectsToAttend(NoSubjectsToAttend.of(6));

            csvReader.close();
            return subjectPool;
        } else
            return null;
    }

    private void setStudentsForSubjectPool(StudentRepository studentRepository, SubjectPool subjectPool, String[] data) {
        FieldOfStudy fieldOfStudy = FieldOfStudy.valueOf(data[SUBJECT_POOL_FIELD_OF_STUDY_COLUMN]);
        val noSemester = org.agh.electer.core.domain.subject.pool.NoSemester.of(Integer.valueOf(data[NO_SEMESTER_COLUMN]));
        StudiesDegree studiesDegree = StudiesDegree.valueOf(data[GRADE_COLUMN].replace(' ', '_').toLowerCase());

        Set<AlbumNumber> studentsForThisSubjectPool = studentRepository.findByFieldOfStudy(fieldOfStudy, noSemester, studiesDegree)
                .stream()
                .map(Student::getAlbumNumber)
                .collect(Collectors.toSet());

        subjectPool.setFieldOfStudy(fieldOfStudy);
        subjectPool.setNoSemester(noSemester);
        subjectPool.setStudiesDegree(studiesDegree);
        subjectPool.setStudents(studentsForThisSubjectPool);
    }

    public void generateFile(HttpServletResponse response, List<Student> students, SubjectPool subjectPool) throws IOException,
            CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {

        String filename = "Zapisy.csv";
        File file = new File(filename);
        val writer = response.getWriter();
        List<String> headersForFile = fillHeaders(subjectPool);

        writer.write(headersForFile.stream().collect(Collectors.joining(";", "", "\n")));

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        students.stream()
                .map(StudentDTOMapper::toDto)
                .map(studentDto -> toCSVData(studentDto, subjectPool))
                .forEach(writer::write);


    }

    private List<String> fillHeaders(SubjectPool subjectPool) {
        val subjectNames = getSubjectNames(subjectPool);
        val subjectHeaders = String.join(";", subjectNames);
//        setSubjectMap(subjectPool, subjectNames);
        return Stream.of("NR ALBUMU", "IMIĘ", "NAZWISKO", "KIERUNEK", "SEMESTR", "STOPIEŃ", "ŚREDNIA OCEN", subjectHeaders).collect(Collectors.toList());
    }

    private List<String> getSubjectNames(SubjectPool subjectPool) {
        return subjectPool.getElectiveSubjects()
                .stream()
                .map(u -> u.getSubjectName().getValue())
                .sorted()
                .collect(Collectors.toList());
    }

//    private void setSubjectMap(SubjectPool subjectPool, List<String> subjectNames) {
//        Set<Subject> subjectObjects = subjectPool.getElectiveSubjects();
//
//        for (val subject : subjectObjects) {
//            for (String subjectName : subjectNames) {
//                if (subjectName.equals(subject.getSubjectName().getValue())) {
//                    subjectsMap.put(subject.getSubjectName().getValue(), subject.getSubjectId().getValue());
//                }
//            }
//        }
//    }

    private Map<String, Boolean> qualifiedOrNotForStudent(StudentDto studentDto, SubjectPool subjectPool) {
        return subjectPool.getElectiveSubjects().stream()
                .collect(Collectors.toMap(s -> s.getSubjectName().getValue(),
                        subject -> subject.getQualifiedStudents().contains(AlbumNumber.of(studentDto.getAlbumNumber()))));
    }

    private String toCSVData(StudentDto studentDto, SubjectPool subjectPool) {
        val map = qualifiedOrNotForStudent(studentDto, subjectPool);
        val qualified = getSubjectNames(subjectPool)
                .stream()
                .map(map::get)
                .map(String::valueOf)
                .collect(Collectors.joining(";")).replace("false", "0").replace("true", "1");


        return Stream.of(studentDto.getAlbumNumber(),
                studentDto.getName(), studentDto.getSurname(),
                String.valueOf(studentDto.getFieldOfStudy()),
                String.valueOf(studentDto.getNumberOfSemester()),
                String.valueOf(studentDto.getStudiesDegree()),
                studentDto.getAverageGrade().toString().replace(".", ","), qualified
        ).collect(Collectors.joining(";", "", "\n"));
    }

}


