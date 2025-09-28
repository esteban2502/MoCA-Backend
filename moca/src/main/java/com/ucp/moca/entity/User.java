package com.ucp.moca.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String firstName;

    @Column(length = 50)
    private String secondName;

    @Column(nullable = false, length = 50)
    private String firstLastName;

    @Column(length = 50)
    private String secondLastName;

    @Column(nullable = false, unique = true, length = 20)
    private String idNumber; // cedula

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 50)
    private String academicLevel; // nivel academico

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role; // ADMIN, PSYCHOLOGIST, PATIENT

    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 500)
    private String notes; // notas adicionales sobre el usuario

    public enum UserRole {
        ADMIN,
        PSYCHOLOGIST,
        PATIENT
    }

    // Método para obtener el nombre completo
    public String getFullName() {
        StringBuilder fullName = new StringBuilder();
        fullName.append(firstName);
        if (secondName != null && !secondName.trim().isEmpty()) {
            fullName.append(" ").append(secondName);
        }
        fullName.append(" ").append(firstLastName);
        if (secondLastName != null && !secondLastName.trim().isEmpty()) {
            fullName.append(" ").append(secondLastName);
        }
        return fullName.toString();
    }

    // Método para obtener las iniciales
    public String getInitials() {
        StringBuilder initials = new StringBuilder();
        initials.append(firstName.charAt(0));
        if (secondName != null && !secondName.trim().isEmpty()) {
            initials.append(secondName.charAt(0));
        }
        initials.append(firstLastName.charAt(0));
        if (secondLastName != null && !secondLastName.trim().isEmpty()) {
            initials.append(secondLastName.charAt(0));
        }
        return initials.toString().toUpperCase();
    }
}
