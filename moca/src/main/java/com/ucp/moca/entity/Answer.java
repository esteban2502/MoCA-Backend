package com.ucp.moca.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "answers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    // Si es opción múltiple, relación a la opción elegida
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id")
    private Option selectedOption ;

    // Si es respuesta abierta (texto)
    private String textResponse;

    // Puntaje asignado por el psicólogo
    private Integer score;


}
