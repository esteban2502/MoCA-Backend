package com.ucp.moca.service.implement;

import com.ucp.moca.entity.User;
import com.ucp.moca.repository.UserRepository;
import com.ucp.moca.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        // Validar que el email no exista
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + user.getEmail());
        }
        
        // Validar que el número de identificación no exista
        if (userRepository.existsByIdNumber(user.getIdNumber())) {
            throw new IllegalArgumentException("El número de identificación ya está registrado: " + user.getIdNumber());
        }
        
        return userRepository.save(user);
    }

    @Override
    public User update(Long id, User userUpdated) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));

        // Validar email único si ha cambiado
        if (!existingUser.getEmail().equals(userUpdated.getEmail()) && 
            userRepository.existsByEmail(userUpdated.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado: " + userUpdated.getEmail());
        }
        
        // Validar número de identificación único si ha cambiado
        if (!existingUser.getIdNumber().equals(userUpdated.getIdNumber()) && 
            userRepository.existsByIdNumber(userUpdated.getIdNumber())) {
            throw new IllegalArgumentException("El número de identificación ya está registrado: " + userUpdated.getIdNumber());
        }

        // Actualizar campos
        existingUser.setFirstName(userUpdated.getFirstName());
        existingUser.setSecondName(userUpdated.getSecondName());
        existingUser.setFirstLastName(userUpdated.getFirstLastName());
        existingUser.setSecondLastName(userUpdated.getSecondLastName());
        existingUser.setIdNumber(userUpdated.getIdNumber());
        existingUser.setPassword(userUpdated.getPassword());
        existingUser.setAcademicLevel(userUpdated.getAcademicLevel());
        existingUser.setBirthDate(userUpdated.getBirthDate());
        existingUser.setEmail(userUpdated.getEmail());
        existingUser.setRole(userUpdated.getRole());
        existingUser.setNotes(userUpdated.getNotes());

        return userRepository.save(existingUser);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario con id " + id + " no encontrado");
        }
        userRepository.deleteById(id);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getByIdNumber(String idNumber) {
        return userRepository.findByIdNumber(idNumber);
    }

    @Override
    public List<User> getByRole(User.UserRole role) {
        return userRepository.findByRole(role);
    }

    @Override
    public List<User> getActiveUsers() {
        return userRepository.findByActiveTrue();
    }

    @Override
    public List<User> getInactiveUsers() {
        return userRepository.findByActiveFalse();
    }

    @Override
    public List<User> getByRoleAndActive(User.UserRole role, boolean active) {
        if (active) {
            return userRepository.findByRoleAndActiveTrue(role);
        } else {
            return userRepository.findByRole(role).stream()
                    .filter(user -> !user.isActive())
                    .toList();
        }
    }

    @Override
    public List<User> searchByName(String name) {
        return userRepository.findByNameContaining(name);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByIdNumber(String idNumber) {
        return userRepository.existsByIdNumber(idNumber);
    }

    @Override
    public long countByRole(User.UserRole role) {
        return userRepository.countByRole(role);
    }

    @Override
    public long countActiveUsers() {
        return userRepository.countByActiveTrue();
    }

    @Override
    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));
        user.setActive(true);
        return userRepository.save(user);
    }

    @Override
    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));
        user.setActive(false);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> authenticate(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password) && user.get().isActive()) {
            return user;
        }
        return Optional.empty();
    }

    @Override
    public User updatePassword(Long id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }

    @Override
    public User updateProfile(Long id, User userProfile) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario con id " + id + " no encontrado"));

        // Actualizar solo campos del perfil (no credenciales)
        existingUser.setFirstName(userProfile.getFirstName());
        existingUser.setSecondName(userProfile.getSecondName());
        existingUser.setFirstLastName(userProfile.getFirstLastName());
        existingUser.setSecondLastName(userProfile.getSecondLastName());
        existingUser.setAcademicLevel(userProfile.getAcademicLevel());
        existingUser.setBirthDate(userProfile.getBirthDate());
        existingUser.setNotes(userProfile.getNotes());

        return userRepository.save(existingUser);
    }
}
