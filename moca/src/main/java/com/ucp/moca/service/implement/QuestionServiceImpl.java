package com.ucp.moca.service.implement;

import com.ucp.moca.entity.Question;
import com.ucp.moca.entity.Test;
import com.ucp.moca.exception.DuplicateQuestionOrderException;
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
    public List<Question> getActiveByTestId(Long id) {
        return questionRepository.getActiveByTestId(id);
    }

    @Override
    public void save(Question question) {
        // Validar que se haya seleccionado una categoría
        if (question.getCategory() == null || question.getCategory().getId() == null) {
            throw new IllegalArgumentException("Debe elegir una categoría para poder crear la pregunta.");
        }
        // Validar que no exista otra pregunta con el mismo orden en el mismo examen
        validateQuestionOrder(question.getTest().getId(), question.getQuestionOrder(), null);
        // Si no se especifica status, establecerlo como true por defecto
        if (question.getStatus() == null) {
            question.setStatus(true);
        }
        questionRepository.save(question);
    }

    @Override
    public void update(Long id, Question questionUpdated) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question con id " + id + " no encontrado"));

        // Validar que no exista otra pregunta con el mismo orden en el mismo examen (excluyendo la pregunta actual)
        validateQuestionOrder(existingQuestion.getTest().getId(), questionUpdated.getQuestionOrder(), id);

        existingQuestion.setQuestion(questionUpdated.getQuestion());
        existingQuestion.setDescription(questionUpdated.getDescription());
        existingQuestion.setQuestionOrder(questionUpdated.getQuestionOrder());
        existingQuestion.setMaxScore(questionUpdated.getMaxScore());
        existingQuestion.setIsDrawing(questionUpdated.getIsDrawing());
        existingQuestion.setStatus(questionUpdated.getStatus());
        existingQuestion.setSupportImage(questionUpdated.getSupportImage());
        existingQuestion.setBackgroundImage(questionUpdated.getBackgroundImage());
        // Actualizar configuración de tabla dinámica (si aplica)
        existingQuestion.setDynamicTableConfig(questionUpdated.getDynamicTableConfig());

        questionRepository.save(existingQuestion);
    }

    @Override
    public void delete(Long id) {
        questionRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Question no encontrada con id " + id));

        // Si status es null, establecerlo como false, sino invertirlo
        if (question.getStatus() == null) {
            question.setStatus(false);
        } else {
            question.setStatus(!question.getStatus());
        }
        questionRepository.save(question);
    }

    private void validateQuestionOrder(Long testId, Integer questionOrder, Long excludeQuestionId) {
        List<Question> existingQuestions = questionRepository.findByTestIdAndQuestionOrder(testId, questionOrder);
        
        // Si estamos actualizando, excluir la pregunta actual de la validación
        if (excludeQuestionId != null) {
            existingQuestions = existingQuestions.stream()
                    .filter(q -> !q.getId().equals(excludeQuestionId))
                    .toList();
        }
        
        if (!existingQuestions.isEmpty()) {
            throw new DuplicateQuestionOrderException(
                "Ya existe una pregunta con el orden " + questionOrder + " en este examen"
            );
        }
    }
}
