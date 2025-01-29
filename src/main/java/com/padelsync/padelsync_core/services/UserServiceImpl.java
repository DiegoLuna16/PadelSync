package com.padelsync.padelsync_core.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.padelsync.padelsync_core.models.Role;
import com.padelsync.padelsync_core.models.User;
import com.padelsync.padelsync_core.repositories.RoleRepository;
import com.padelsync.padelsync_core.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository repository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User save(User user) {

        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        
        optionalRole.ifPresent(roles::add);
        user.setRoles(roles);
        
        if(user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);

        }

        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
         return (List<User>) repository.findAll();
    }

    @Override
    public Optional<User> update(Long id, User user) {
        Optional<User> optionalUser = repository.findById(id);
        if(optionalUser.isPresent()) {
            User userDb = optionalUser.orElseThrow();

            userDb.setUsername(user.getUsername());
            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPhone(user.getPhone());
            userDb.setCountryCode(user.getCountryCode());
            userDb.setPassword(user.getPassword());
            userDb.setBirthdate(user.getBirthdate());
            // userDb.setGender(user.getGender());
            // userDb.setEnabled(user.isEnabled());
            userDb.setAvatar(user.getAvatar());
            // userDb.setCreatedAt(user.getCreatedAt());
            userDb.setUpdatedAt(LocalDateTime.now());
            return Optional.of(repository.save(userDb));

        }
        return optionalUser;
    }

    @Override
    public Optional<User> delete(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        optionalUser.ifPresent(userDb -> {
            repository.delete(userDb);
        });
        return optionalUser;
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

}
