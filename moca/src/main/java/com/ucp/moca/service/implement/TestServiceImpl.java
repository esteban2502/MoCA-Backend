package com.ucp.moca.service.implement;

import com.ucp.moca.entity.Test;
import com.ucp.moca.repository.TestRepository;
import com.ucp.moca.service.TestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    public TestServiceImpl(TestRepository testRepository){
        this.testRepository = testRepository;
    }

    @Override
    public List<Test> getAll() {
        return testRepository.findAll();
    }

    @Override
    public void save(Test test) {
        testRepository.save(test);
    }

    @Override
    public void update(Long id, Test testUpdated) {
        Test existingTest = testRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Test con id " + id + " no encontrado"));

        existingTest.setTitle(testUpdated.getTitle());
        existingTest.setDescription(testUpdated.getDescription());

        testRepository.save(existingTest);
    }

    @Override
    public void delete(Long id) {
         testRepository.deleteById(id);
    }
}
