package com.ucp.moca.controller;

import com.ucp.moca.entity.Answer;
import com.ucp.moca.service.AnswerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/answer/v1")
public class AnswerController {

    private final AnswerService answerService;

    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @GetMapping("/question/{questionId}")
    public ResponseEntity<List<Answer>> getAllByQuestionId(@PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.getAllByQuestionId(questionId));
    }

    @GetMapping("/test/{testId}")
    public ResponseEntity<List<Answer>> getAllByTestId(@PathVariable Long testId) {
        return ResponseEntity.ok(answerService.getAllByTestId(testId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Answer> getById(@PathVariable Long id) {
        return ResponseEntity.ok(answerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Answer answer) {
        answerService.save(answer);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Answer answer) {
        answerService.update(id, answer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        answerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
