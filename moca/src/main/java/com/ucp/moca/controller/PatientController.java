package com.ucp.moca.controller;

import com.ucp.moca.entity.UserEntity;
import com.ucp.moca.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/patients/v1")
public class PatientController {

    @Autowired
    private UserEntityRepository userEntityRepository;

    @GetMapping("/cedula/{idNumber}")
    public ResponseEntity<UserEntity> getPatientByCedula(@PathVariable String idNumber) {
        Optional<UserEntity> patient = userEntityRepository.findUserEntityByIdNumber(idNumber);
        if (patient.isPresent()) {
            return ResponseEntity.ok(patient.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<UserEntity> registerPatient(@RequestBody UserEntity patient) {
        // Asignar rol de paciente por defecto
        patient.setActive(true);
        patient.setEnabled(true);
        patient.setAccountNoExpired(true);
        patient.setAccountNoLocked(true);
        patient.setCredentialNoExpired(true);
        
        UserEntity savedPatient = userEntityRepository.save(patient);
        return ResponseEntity.ok(savedPatient);
    }
}
