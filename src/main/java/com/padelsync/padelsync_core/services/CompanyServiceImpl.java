package com.padelsync.padelsync_core.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.padelsync.padelsync_core.models.Company;
import com.padelsync.padelsync_core.repositories.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    CompanyRepository repository;

    @Override
    public List<Company> findAll() {
        return (List<Company>) repository.findAll();
    }

    @Override
    public Company save(Company academy) {
        return repository.save(academy);
    }

    @Override
    public Optional<Company> update(Long id, Company academy) {
        Optional<Company> academyOptional = repository.findById(id);
        if(academyOptional.isPresent()) {
            Company academyDB = academyOptional.orElseThrow();
            academyDB.setName(academy.getName());
            academyDB.setLegalName(academy.getLegalName());
            academyDB.setAddress(academy.getAddress());
            academyDB.setCountry(academy.getCountry());
            academyDB.setCity(academy.getCity());
            academyDB.setPostalCode(academy.getPostalCode());
            academyDB.setLanguage(academy.getLanguage());
            academyDB.setLatLong(academy.getLatLong());
            academyDB.setDescription(academy.getDescription());
            academyDB.setAvatar(academy.getAvatar());
            academyDB.setUpdatedAt(LocalDateTime.now());
            return Optional.of(repository.save(academyDB));
        }
        return academyOptional;

    }

    @Override
    public Optional<Company> delete(Long id) {
        Optional<Company> optionalAcademy = repository.findById(id);
        optionalAcademy.ifPresent(repository::delete);
        return optionalAcademy;
    }

}
