package org.agh.electer.core.service;

import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
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

@Service
public class ComputingServiceImpl implements ComputingService {
    private final SubjectPoolRepository subjectPoolRepository;
    private final StudentRepository studentRepository;
    private final SubjectChoiceRepository subjectChoiceRepository;

    private Map<AlbumNumber, Student> studentMap;
    private Map<SubjectChoiceId, Optional<SubjectChoice>> subjectChoiceMap;

    @Autowired
    public ComputingServiceImpl(SubjectPoolRepository subjectPoolRepository, StudentRepository studentRepository, SubjectChoiceRepository subjectChoiceRepository) {
        this.subjectPoolRepository = subjectPoolRepository;
        this.studentRepository = studentRepository;
        this.subjectChoiceRepository = subjectChoiceRepository;
    }

    @Override
    public void compute(SubjectPoolId subjectPoolId) {
        val subjectPool = subjectPoolRepository.findById(subjectPoolId).get();

        for (val subject : subjectPool.getElectiveSubjects()) {
            List<AlbumNumber> studentsQualifiedForThisSubject = new ArrayList<>();

            studentMap = subjectPoolRepository.selectSubjectChoicesForSubject(subject.getSubjectId().getValue())
                    .stream()
                    .map(SubjectChoice::getStudentId)
                    .collect(Collectors.toMap(Function.identity(), id -> studentRepository.findById(id).get()));


            // Lista studentow ktorzy dokonywali wyborow

            List<Student> studentSList = new ArrayList<>(studentMap.values());

            // iteracja po liscie studentow w celu wylonienia starosty
            // i ustawienia mu przedmiotow jak chce ...

            for (val student : studentSList) {
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

            List<SubjectChoice> subjectChoicesByPriorityAndAvg = studentMap.values()
                    .stream()
                    .map(student -> student.findSubjectChoiceBySubjectId(subject.getSubjectId()))
                    .sorted(compareByPriority()
                            .thenComparing(compareByAgerageGrade()))
                    .collect(Collectors.toList());

            int sinceWhichIndex = 0;
            for (int i = 0; i < subject.getNumberOfPlaces().getValue(); i++) {
                if (i <= subjectChoicesByPriorityAndAvg.size()) {
                    studentsQualifiedForThisSubject.add(subjectChoicesByPriorityAndAvg.get(i).getStudentId());
                } else {
                    sinceWhichIndex = i;
                    studentMap.values().forEach(student -> student.deleteSubjectChoice(subject.getSubjectId()));
                    break;
                }
                studentsQualifiedForThisSubject.clear();
            }

            for (int i = sinceWhichIndex; i < subjectChoicesByPriorityAndAvg.size(); i++) {
                val subjectchoice = subjectChoicesByPriorityAndAvg.get(i);
                studentMap.get(subjectchoice.getStudentId()).decreasePriority();
            }


        }
    }

    private Comparator<SubjectChoice> compareByPriority() {
        return Comparator.comparing(s -> s.getPriority().getValue());
    }

    private Comparator<SubjectChoice> compareByAgerageGrade() {
        return Comparator.comparing(s -> studentMap.get(s.getStudentId()).getAverageGrade().getValue());
    }
}
