package org.agh.electer.core.service;

import lombok.val;
import org.agh.electer.core.domain.student.AlbumNumber;
import org.agh.electer.core.domain.student.Student;
import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;
import org.agh.electer.core.domain.subject.pool.SubjectPoolId;
import org.agh.electer.core.infrastructure.repositories.StudentRepository;
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

    private Map<AlbumNumber, Student> studentMap;
    private Map<SubjectChoiceId, Optional<SubjectChoice>> subjectChoiceMap;

    @Autowired
    public ComputingServiceImpl(SubjectPoolRepository subjectPoolRepository, StudentRepository studentRepository) {
        this.subjectPoolRepository = subjectPoolRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void compute(SubjectPoolId subjectPoolId) {
        for (val subject : subjectPoolRepository.findById(subjectPoolId).get().getElectiveSubjects()) {
            List<AlbumNumber> studentsQualifiedForThisSubject = new ArrayList<>();

            studentMap = subject.getSubjectChoices().stream()
                    .map(SubjectChoice::getStudent)
                    .collect(Collectors.toMap(Function.identity(), id -> studentRepository.findById(id).get()));

            List<SubjectChoice> subjectChoicesByPriorityAndAvg = subject.getSubjectChoices()
                    .stream()
                    .sorted(compareByPriority()
                            .thenComparing(compareByAgerageGrade()))
                    .collect(Collectors.toList());

            int sinceWhichIndex = 0;
            for (int i = 0; i < subject.getNumberOfPlaces().getValue(); i++) {
                if (i <= subjectChoicesByPriorityAndAvg.size()) {
                    studentsQualifiedForThisSubject.add(subjectChoicesByPriorityAndAvg.get(i).getStudent());
                } else {
                    sinceWhichIndex = i;
                    studentMap.values().forEach(student -> student.deleteSubjectChoice(subject.getSubjectId()));
                    break;
                }
                studentsQualifiedForThisSubject.clear();
            }

            for (int i = sinceWhichIndex; i < subjectChoicesByPriorityAndAvg.size(); i++) {
                val subjectchoice = subjectChoicesByPriorityAndAvg.get(i);
                studentMap.get(subjectchoice.getStudent()).decreasePriority();
            }


        }
    }

    private Comparator<SubjectChoice> compareByPriority() {
        return Comparator.comparing(s -> s.getPriority().getValue());
    }

    private Comparator<SubjectChoice> compareByAgerageGrade() {
        return Comparator.comparing(s -> studentRepository.findById(s.getStudent()).get().getAverageGrade().getValue());
    }
}
