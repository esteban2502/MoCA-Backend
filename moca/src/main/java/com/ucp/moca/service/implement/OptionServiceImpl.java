package com.ucp.moca.service.implement;

import com.ucp.moca.entity.Option;
import com.ucp.moca.repository.OptionRepository;
import com.ucp.moca.service.OptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;

    public OptionServiceImpl(OptionRepository optionRepository) {
        this.optionRepository = optionRepository;
    }

    @Override
    public List<Option> getAllByQuestionId(Long questionId) {
        return optionRepository.findByQuestionId(questionId);
    }

    @Override
    public Option getById(Long id) {
        return optionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Option con id " + id + " no encontrado"));
    }

    @Override
    public void save(Option option) {
        optionRepository.save(option);
    }

    @Override
    public void update(Long id, Option optionUpdated) {
        Option existingOption = optionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Option con id " + id + " no encontrado"));

        existingOption.setText(optionUpdated.getText());
        existingOption.setQuestion(optionUpdated.getQuestion());

        optionRepository.save(existingOption);
    }

    @Override
    public void delete(Long id) {
        optionRepository.deleteById(id);
    }
}
