package com.padelsync.padelsync_core.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.padelsync.padelsync_core.models.Role;

public interface RoleRepository extends CrudRepository<Role,Long>{
    
    Optional<Role> findByName(String name);

}
