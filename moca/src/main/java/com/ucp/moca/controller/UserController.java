package com.ucp.moca.controller;

import com.ucp.moca.entity.User;
import com.ucp.moca.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user/v1")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        return ResponseEntity.ok(userService.getAll());
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        Optional<User> user = userService.getById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo usuario
    @PostMapping
    public ResponseEntity<User> create(@RequestBody User user) {
        try {
            User createdUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Actualizar usuario completo
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        try {
            User updatedUser = userService.update(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            userService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Obtener usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getByEmail(@PathVariable String email) {
        Optional<User> user = userService.getByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener usuario por número de identificación
    @GetMapping("/id-number/{idNumber}")
    public ResponseEntity<User> getByIdNumber(@PathVariable String idNumber) {
        Optional<User> user = userService.getByIdNumber(idNumber);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Obtener usuarios por rol
    @GetMapping("/role/{role}")
    public ResponseEntity<List<User>> getByRole(@PathVariable User.UserRole role) {
        return ResponseEntity.ok(userService.getByRole(role));
    }

    // Obtener usuarios activos
    @GetMapping("/active")
    public ResponseEntity<List<User>> getActiveUsers() {
        return ResponseEntity.ok(userService.getActiveUsers());
    }

    // Obtener usuarios inactivos
    @GetMapping("/inactive")
    public ResponseEntity<List<User>> getInactiveUsers() {
        return ResponseEntity.ok(userService.getInactiveUsers());
    }

    // Buscar usuarios por nombre
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchByName(@RequestParam String name) {
        return ResponseEntity.ok(userService.searchByName(name));
    }

    // Activar usuario
    @PatchMapping("/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable Long id) {
        try {
            User user = userService.activateUser(id);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Desactivar usuario
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable Long id) {
        try {
            User user = userService.deactivateUser(id);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar contraseña
    @PatchMapping("/{id}/password")
    public ResponseEntity<User> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest request) {
        try {
            User user = userService.updatePassword(id, request.getNewPassword());
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Actualizar perfil (sin credenciales)
    @PatchMapping("/{id}/profile")
    public ResponseEntity<User> updateProfile(@PathVariable Long id, @RequestBody User userProfile) {
        try {
            User user = userService.updateProfile(id, userProfile);
            return ResponseEntity.ok(user);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Autenticación básica
    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticate(@RequestBody AuthenticationRequest request) {
        Optional<User> user = userService.authenticate(request.getEmail(), request.getPassword());
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    // Verificar si existe email
    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

    // Verificar si existe número de identificación
    @GetMapping("/exists/id-number/{idNumber}")
    public ResponseEntity<Boolean> existsByIdNumber(@PathVariable String idNumber) {
        return ResponseEntity.ok(userService.existsByIdNumber(idNumber));
    }

    // Contar usuarios por rol
    @GetMapping("/count/role/{role}")
    public ResponseEntity<Long> countByRole(@PathVariable User.UserRole role) {
        return ResponseEntity.ok(userService.countByRole(role));
    }

    // Contar usuarios activos
    @GetMapping("/count/active")
    public ResponseEntity<Long> countActiveUsers() {
        return ResponseEntity.ok(userService.countActiveUsers());
    }

    // Clases internas para requests
    public static class PasswordUpdateRequest {
        private String newPassword;

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    public static class AuthenticationRequest {
        private String email;
        private String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
