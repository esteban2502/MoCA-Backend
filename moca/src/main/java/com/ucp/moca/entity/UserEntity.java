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
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

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

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 50)
    private String academicLevel; // nivel academico

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false, unique = true)
    private String email;



    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name="user_roles",joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<RoleEntity> roles = new HashSet<>(); //  PSYCHOLOGIST, PATIENT

    @Column(nullable = false)
    private boolean active = true;

    @Column(length = 500)
    private String notes; // notas adicionales sobre el usuario

    /*
    * Propiedades Spring Security
    * */

    @Column(name = "is_enabled")
    private boolean isEnabled;
    @Column(name = "account_No_Expired")
    private boolean accountNoExpired;
    @Column(name = "account_No_Locked")
    private boolean accountNoLocked;
    @Column(name = "credential_No_Expired")
    private boolean credentialNoExpired;


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


}
