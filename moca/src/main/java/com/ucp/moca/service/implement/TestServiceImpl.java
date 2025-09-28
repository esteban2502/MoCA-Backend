package com.ucp.moca.service.implement;

import com.ucp.moca.dto.TestUpdateRequest;
import com.ucp.moca.entity.Test;
import com.ucp.moca.exception.TestWithQuestionsException;
import com.ucp.moca.repository.QuestionRepository;
import com.ucp.moca.repository.TestRepository;
import com.ucp.moca.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    public TestServiceImpl(TestRepository testRepository, QuestionRepository questionRepository){
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<Test> getAll() {
        List<Test> tests = testRepository.findAllOrderByIdDesc();
        for (Test test : tests) {
            Long count = questionRepository.countByTestId(test.getId());
            test.setNumQuestions(count);
        }
        return tests;
    }

    @Override
    public void save(Test test) {
        testRepository.save(test);
    }

    @Override
    public void update(Long id, TestUpdateRequest testUpdated) {
        Test existingTest = testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test con id " + id + " no encontrado"));

        existingTest.setTitle(testUpdated.getTitle());
        existingTest.setDescription(testUpdated.getDescription());
        existingTest.setStatus(testUpdated.isStatus());

        testRepository.save(existingTest);
    }

    @Override
    public void delete(Long id) {
        // Verificar si el test tiene preguntas asociadas
        Long questionCount = questionRepository.countByTestId(id);
        if (questionCount > 0) {
            throw new TestWithQuestionsException(
                "No se puede eliminar el examen porque tiene " + questionCount + " pregunta(s) asociada(s). " +
                "Primero debe eliminar todas las preguntas del examen."
            );
        }
        
        testRepository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test no encontrado con id " + id));

        test.setStatus(!test.isStatus());
        testRepository.save(test);
    }
}
