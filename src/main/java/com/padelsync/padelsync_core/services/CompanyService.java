package com.padelsync.padelsync_core.services;

import java.util.List;
import java.util.Optional;

import com.padelsync.padelsync_core.models.Company;

public interface CompanyService {

    List<Company> findAll();
    Company save (Company academy);
    Optional<Company> update(Long id, Company academy);
    Optional<Company> delete (Long id);
}
