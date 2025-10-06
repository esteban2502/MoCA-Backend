package com.ucp.moca.controller;

import com.ucp.moca.entity.Patient;
import com.ucp.moca.entity.UserEntity;
import com.ucp.moca.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/patients/v1")
public class PatientController {

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        return ResponseEntity.ok(patients);
    }

    @GetMapping("/my-patients")
    public ResponseEntity<List<Patient>> getMyPatients() {
        try {
            UserEntity currentUser = getCurrentUser();
            System.out.println("🔍 Buscando pacientes para psicólogo: " + currentUser.getFullName() + " (ID: " + currentUser.getId() + ")");
            
            List<Patient> patients = patientRepository.findByPsychologistsId(currentUser.getId());
            System.out.println("📊 Pacientes encontrados: " + patients.size());
            
            // Debug: mostrar detalles de cada paciente encontrado
            for (Patient patient : patients) {
                System.out.println("  - Paciente: " + patient.getFullName() + 
                                 " (ID: " + patient.getId() + 
                                 ", Psicólogos: " + patient.getPsychologists().size() + ")");
            }
            
            return ResponseEntity.ok(patients);
        } catch (RuntimeException e) {
            System.err.println("❌ Error obteniendo usuario autenticado: " + e.getMessage());
            return ResponseEntity.ok(List.of());
        }
    }

    @GetMapping("/cedula/{idNumber}")
    public ResponseEntity<Patient> getPatientByCedula(@PathVariable String idNumber) {
        Optional<Patient> patient = patientRepository.findByDocumentNumber(idNumber);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<Patient> registerPatient(@RequestBody Patient patient) {
        // Los pacientes se registran sin psicólogos asignados
        // Los psicólogos se asignan cuando evalúan al paciente
        Patient saved = patientRepository.save(patient);
        System.out.println("✅ Paciente registrado: " + saved.getFullName());
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/debug-all-patients")
    public ResponseEntity<String> debugAllPatients() {
        try {
            UserEntity currentUser = getCurrentUser();
            List<Patient> allPatients = patientRepository.findAll();
            
            StringBuilder debug = new StringBuilder();
            debug.append("Usuario actual: ").append(currentUser.getFullName()).append(" (ID: ").append(currentUser.getId()).append(")\n");
            debug.append("Total pacientes en BD: ").append(allPatients.size()).append("\n\n");
            
            for (Patient patient : allPatients) {
                debug.append("Paciente: ").append(patient.getFullName())
                     .append(" (ID: ").append(patient.getId())
                     .append(", Psicólogos: ").append(patient.getPsychologists().size()).append(")\n");
                
                for (UserEntity psychologist : patient.getPsychologists()) {
                    debug.append("  - Psicólogo: ").append(psychologist.getFullName())
                         .append(" (ID: ").append(psychologist.getId()).append(")\n");
                }
            }
            
            System.out.println("🔍 Debug todos los pacientes:\n" + debug.toString());
            return ResponseEntity.ok(debug.toString());
        } catch (RuntimeException e) {
            System.err.println("Error en debug: " + e.getMessage());
            return ResponseEntity.status(500).body("Error en debug: " + e.getMessage());
        }
    }


    private UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserEntity) {
            return (UserEntity) authentication.getPrincipal();
        }
        throw new RuntimeException("Usuario no autenticado");
    }
}
