package com.ucp.moca.service;

import com.ucp.moca.entity.Option;

import java.util.List;

public interface OptionService {
    List<Option> getAllByQuestionId(Long questionId);
    Option getById(Long id);
    void save(Option option);
    void update(Long id, Option optionUpdated);
    void delete(Long id);
}
