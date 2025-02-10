package com.padelsync.padelsync_core.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.padelsync.padelsync_core.models.User;

public interface UserRepository extends CrudRepository<User,Long>{

    boolean existsByUsername(String username);
    // Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
