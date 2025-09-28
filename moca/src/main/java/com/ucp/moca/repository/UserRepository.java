package com.ucp.moca.repository;

import com.ucp.moca.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    // Buscar por email
    Optional<User> findByEmail(String email);
    
    // Buscar por número de identificación (cédula)
    Optional<User> findByIdNumber(String idNumber);
    
    // Buscar por rol
    List<User> findByRole(User.UserRole role);
    
    // Buscar usuarios activos
    List<User> findByActiveTrue();
    
    // Buscar usuarios inactivos
    List<User> findByActiveFalse();
    
    // Buscar por rol y estado activo
    List<User> findByRoleAndActiveTrue(User.UserRole role);
    
    // Buscar por nombre o apellido (búsqueda parcial)
    @Query("SELECT u FROM User u WHERE " +
           "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.secondName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.firstLastName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
           "LOWER(u.secondLastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContaining(@Param("name") String name);
    
    // Verificar si existe un email
    boolean existsByEmail(String email);
    
    // Verificar si existe un número de identificación
    boolean existsByIdNumber(String idNumber);
    
    // Contar usuarios por rol
    long countByRole(User.UserRole role);
    
    // Contar usuarios activos
    long countByActiveTrue();
}
