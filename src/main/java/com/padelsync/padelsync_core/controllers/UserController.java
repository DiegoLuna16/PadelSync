package com.padelsync.padelsync_core.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.padelsync.padelsync_core.models.User;
import com.padelsync.padelsync_core.services.UserService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:5173")
public class UserController {

    @Autowired
    UserService service;

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @PostMapping("/register")
    public ResponseEntity<?> save(@Valid @RequestBody User user,BindingResult result) {
        if(result.hasFieldErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Long id) {
        Optional<User> userOptional = service.update(id, user);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.orElseThrow());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found with ID: " + id);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete (@PathVariable Long id) { 
        Optional<User> userOptional = service.delete(id);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

        private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(),"El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }



}
