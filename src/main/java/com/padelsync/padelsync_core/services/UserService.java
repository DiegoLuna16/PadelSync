package com.padelsync.padelsync_core.services;

import java.util.List;
import java.util.Optional;

import com.padelsync.padelsync_core.models.User;

public interface UserService{

    User save(User user);
    User findById(Long id);
    List<User> findAll();
    Optional<User> update(Long id, User user);
    Optional<User> delete(Long id);
    boolean existsByUsername(String username);



}
