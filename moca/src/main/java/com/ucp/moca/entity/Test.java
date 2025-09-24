package com.ucp.moca.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "test")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private boolean status;

    @OneToMany(mappedBy = "test", fetch = FetchType.LAZY)
    @JsonManagedReference
    @JsonIgnore
    private List<Question> questions;


    @Transient
    private Long numQuestions; // No se guarda en la BD




}
