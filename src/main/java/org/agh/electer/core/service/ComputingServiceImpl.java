package org.agh.electer.core.service;

import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.NoPlaces;
import org.agh.electer.core.domain.subject.Subject;
import org.agh.electer.core.domain.subject.choice.Priority;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.domain.subject.pool.SubjectPool;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.entities.StudentsRole;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectChoiceRepository;
import org.agh.electer.core.infrastructure.repositories.SubjectPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class ComputingServiceImpl implements ComputingService {
    private final SubjectPoolRepository subjectPoolRepository;
    private final StudentRepository studentRepository;

    private Map<AlbumNumber, Student> studentMap;
    private Map<SubjectChoiceId, Optional<SubjectChoice>> subjectChoiceMap;

    @Autowired
    public ComputingServiceImpl(SubjectPoolRepository subjectPoolRepository, StudentRepository studentRepository, SubjectChoiceRepository subjectChoiceRepository) {
        this.subjectPoolRepository = subjectPoolRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void compute(SubjectPoolId subjectPoolId) {
        val subjectPool = subjectPoolRepository.findById(subjectPoolId).get();

        val studentList = subjectPool.getStudents().stream().map(studentRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toMap(Student::getAlbumNumber, it->it));
        val subjects=subjectPool.getElectiveSubjects().stream().sorted(compareByNoPrioritiesSorted()).collect(Collectors.toList());
        for (val subject : subjects) {
            if(subject.getNumberOfPlaces().getValue()==0){
                subject.setNumberOfPlaces(NoPlaces.of(Integer.MAX_VALUE));
            }
            List<AlbumNumber> studentsQualifiedForThisSubject = new ArrayList<>();

            studentMap = subjectPoolRepository.selectSubjectChoicesForSubject(subject.getSubjectId().getValue())
                    .stream()
                    .map(SubjectChoice::getStudentId)
                    .collect(Collectors.toMap(Function.identity(), studentList::get));


            // Lista studentow ktorzy dokonywali wyborow
            List<Student> studentSList = new ArrayList<>(studentMap.values());

            // iteracja po liscie studentow w celu wylonienia starosty
            // i ustawienia mu przedmiotow jak chce ...
            setRepresentativesChoices(subjectPool, subject, studentsQualifiedForThisSubject, studentSList);

            //ustawienie kolejki do przedmiotu
            List<SubjectChoice> subjectChoicesByPriorityAndAvg = studentMap.values()
                    .stream()
                    .filter(student -> student.getNoQualifiedForSubjects()<subjectPool.getNoSubjectsToAttend().getValue())
                    .map(student -> student.findSubjectChoiceBySubjectId(subject.getSubjectId()))
                    .sorted(compareByPriority()
                            .thenComparing(compareByAgerageGrade()))
                    .collect(Collectors.toList());

            //wpisanie do przedmiotu zakwalifikowanych osob
            //i obnizenie priorytetow + usuniecie wyborow niezakwalifikowanych
            //poczawszy od indeksu:
            int sinceWhichIndex = 0;
            for (int i = 0; i < subject.getNumberOfPlaces().getValue(); i++) {
                if (i < subjectChoicesByPriorityAndAvg.size()) {
                    studentsQualifiedForThisSubject.add(subjectChoicesByPriorityAndAvg.get(i).getStudentId());
                    studentMap.get(subjectChoicesByPriorityAndAvg.get(i).getStudentId()).increaseNoQualiiedFoSubjects();
                } else {
                    sinceWhichIndex = i;
                    studentMap.values().forEach(student -> student.deleteSubjectChoice(subject.getSubjectId()));
                    break;
                }
                subject.setQualifiedStudents(studentsQualifiedForThisSubject);
//                studentsQualifiedForThisSubject.clear();
            }

            for (int i = sinceWhichIndex; i < subjectChoicesByPriorityAndAvg.size(); i++) {
                val subjectchoice = subjectChoicesByPriorityAndAvg.get(i);
                studentMap.get(subjectchoice.getStudentId()).decreasePriority();
            }


        }
        subjectPoolRepository.update(subjectPool);
    }

    private void setRepresentativesChoices(SubjectPool subjectPool,
                                           Subject subject,
                                           List<AlbumNumber> studentsQualifiedForThisSubject,
                                           List<Student> allStudentSList) {
        for (val student : allStudentSList) {
            AlbumNumber albumNumber = student.getAlbumNumber();

            // ---> jesli dany student jest starosta...
            if (student.getStudentsRole().equals(StudentsRole.YearGroupRepresentative)) {

                // ---> to jesli wybral ten przedmiot z priorytetem <= liczbie przedmiotow na jakie musi uczeszczac...
                if (subjectPool.getNoSubjectsToAttend().getValue() >=
                        student.getSubjectChoicePriority(subject.getSubjectId()).getValue()) {

                    // ---> to dodajemy go do listy zakwalifikowanych...
                    studentsQualifiedForThisSubject.add(albumNumber);
                    subject.decreaseNoPlaces();
                }

                // ---> niezaleznie jaki byl priorytet usuwamy staroste z mapy
                // (albo jest juz zapisany albo nie chcial byc)
                //  tak czy tak nie bierzemy go pod uwage w dalszych zapisach
                studentMap.remove(albumNumber);
            }
        }
    }

    private Comparator<SubjectChoice> compareByPriority() {
        return Comparator.comparing(s -> s.getPriority().getValue());
    }

    private Comparator<SubjectChoice> compareByAgerageGrade() {
        return Comparator.comparing(s -> studentMap.get(s.getStudentId()).getAverageGrade().getValue());
    }

    private Comparator<Subject> compareByNoPrioritiesSorted() {
        return Comparator.comparing(Subject::getMostCommonPriority).reversed();
    }

}
