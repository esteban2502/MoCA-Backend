package com.ucp.moca.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id", nullable = false)
    @JsonIgnoreProperties({"options", "test", "category"})
    private Question question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id", nullable = false)
    @JsonIgnoreProperties({"answers"})
    private Result result;

    // Respuesta del paciente (puede contener texto o imagen base64 para preguntas de dibujar)
    @Column(columnDefinition = "TEXT")
    private String userAnswer;

    // Si es respuesta abierta (texto)
    @Column(columnDefinition = "TEXT")
    private String textResponse;

    // Puntaje asignado por el psicólogo
    private Integer score;

    // Observaciones del evaluador
    @Column(nullable = false, length = 500)
    private String notes;

    // Respuesta de tabla dinámica (JSON con celdas seleccionadas)
    // Ejemplo:
    // {
    //   "0-1": true,
    //   "1-0": true
    // }
    @Column(name = "dynamic_table_response", columnDefinition = "TEXT")
    private String dynamicTableResponse;

}
