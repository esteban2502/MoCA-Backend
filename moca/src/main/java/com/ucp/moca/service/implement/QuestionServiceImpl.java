package com.ucp.moca.service.implement;

import com.ucp.moca.entity.Question;
import com.ucp.moca.entity.Test;
import com.ucp.moca.repository.QuestionRepository;
import com.ucp.moca.service.QuestionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository){
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Question> getAllByTestId(Long id) {
        return questionRepository.getAllByTestId(id);
    }

    @Override
    public void save(Question question) {
        questionRepository.save(question);
    }

    @Override
    public void update(Long id, Question questionUpdated) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question con id " + id + " no encontrado"));

        existingQuestion.setQuestion(questionUpdated.getQuestion());
        existingQuestion.setDescription(questionUpdated.getDescription());

        questionRepository.save(existingQuestion);
    }

    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }
}
