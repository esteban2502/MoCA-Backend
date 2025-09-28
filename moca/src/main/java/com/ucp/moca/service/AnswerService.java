package com.ucp.moca.service;

import com.ucp.moca.entity.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAllByQuestionId(Long questionId);
    List<Answer> getAllByTestId(Long testId);
    Answer getById(Long id);
    void save(Answer answer);
    void update(Long id, Answer answerUpdated);
    void delete(Long id);
}
