package com.ucp.moca.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String documentNumber;

    @Column(nullable = false)
    private String fullName;

    private LocalDate birthDate;

    // Sexo del paciente: Hombre, Mujer, Otro
    @Column(name = "sex", length = 20)
    private String sex;

    // Descripción cuando el sexo es "Otro" (máx 35 caracteres)
    @Column(name = "sex_other_description", length = 35)
    private String sexOtherDescription;

    // Nivel de educación del paciente
    // Valores esperados:
    // - Educación Inicial
    // - Educación Preescolar
    // - Educación Basica (Primaria y Secundaria)
    // - Educación Media (Bachillerato)
    // - Educación superior
    @Column(name = "education_level", length = 60)
    private String educationLevel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "patient_psychologists",
        joinColumns = @JoinColumn(name = "patient_id"),
        inverseJoinColumns = @JoinColumn(name = "psychologist_id")
    )
    @JsonIgnoreProperties({"roles", "password", "patients"})
    private Set<UserEntity> psychologists = new HashSet<>(); // psicólogos que han evaluado al paciente
}


