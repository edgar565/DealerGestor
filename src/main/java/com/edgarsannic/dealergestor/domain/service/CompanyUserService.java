/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.CompanyUser;

import java.util.List;

public interface CompanyUserService {

    List<CompanyUser> findAllCompanyUsers();
    CompanyUser findCompanyUserById(Long id);
    CompanyUser saveCompanyUser(CompanyUser user);
    CompanyUser updateCompanyUser(Long id, CompanyUser user);
    void deleteCompanyUser(Long id);
    public CompanyUser findByUsername(String username);
}