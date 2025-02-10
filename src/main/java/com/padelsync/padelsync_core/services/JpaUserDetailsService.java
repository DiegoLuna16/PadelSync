package com.padelsync.padelsync_core.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.padelsync.padelsync_core.models.User;
import com.padelsync.padelsync_core.repositories.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = repository.findByEmail(email); 
    
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException(String.format("Email %s no existe en el sistema!", email));
        }
    
        User user = optionalUser.orElseThrow();
    
        List<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
    
        return new org.springframework.security.core.userdetails.User(
            user.getEmail(), // Usar email en lugar de username
            user.getPassword(),
            user.isEnabled(),
            true,
            true,
            true,
            authorities);
    }
    

}
