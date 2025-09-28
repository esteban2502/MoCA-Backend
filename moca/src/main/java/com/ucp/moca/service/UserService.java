package com.ucp.moca.service;

import com.ucp.moca.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    
    // Operaciones básicas CRUD
    List<User> getAll();
    Optional<User> getById(Long id);
    User save(User user);
    User update(Long id, User userUpdated);
    void delete(Long id);
    
    // Operaciones específicas de búsqueda
    Optional<User> getByEmail(String email);
    Optional<User> getByIdNumber(String idNumber);
    List<User> getByRole(User.UserRole role);
    List<User> getActiveUsers();
    List<User> getInactiveUsers();
    List<User> getByRoleAndActive(User.UserRole role, boolean active);
    List<User> searchByName(String name);
    
    // Operaciones de validación
    boolean existsByEmail(String email);
    boolean existsByIdNumber(String idNumber);
    
    // Operaciones de conteo
    long countByRole(User.UserRole role);
    long countActiveUsers();
    
    // Operaciones de estado
    User activateUser(Long id);
    User deactivateUser(Long id);
    
    // Operaciones de autenticación (básicas)
    Optional<User> authenticate(String email, String password);
    
    // Operaciones de actualización específicas
    User updatePassword(Long id, String newPassword);
    User updateProfile(Long id, User userProfile);
}
