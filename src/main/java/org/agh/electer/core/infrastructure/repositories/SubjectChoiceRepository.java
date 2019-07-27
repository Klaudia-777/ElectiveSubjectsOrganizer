package org.agh.electer.core.infrastructure.repositories;

import org.agh.electer.core.domain.subject.choice.SubjectChoice;
import org.agh.electer.core.domain.subject.choice.SubjectChoiceId;

public interface SubjectChoiceRepository{
    SubjectChoice findById(SubjectChoiceId subjectChoiceId);
}
