package com.ucp.moca.repository;

import com.ucp.moca.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query("SELECT q FROM Question q WHERE q.test.id = :id")
    List<Question> getAllByTestId(@Param("id") Long id);

    Long countByTestId(Long testId);

}
