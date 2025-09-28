package com.ucp.moca.controller;

import com.ucp.moca.entity.Option;
import com.ucp.moca.service.OptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/option/v1")
public class OptionController {

    private final OptionService optionService;

    public OptionController(OptionService optionService) {
        this.optionService = optionService;
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Option>> getAllByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(optionService.getAllByQuestionId(questionId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Option> getById(@PathVariable Long id) {
        return ResponseEntity.ok(optionService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Option option) {
        optionService.save(option);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Option option) {
        optionService.update(id, option);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        optionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
