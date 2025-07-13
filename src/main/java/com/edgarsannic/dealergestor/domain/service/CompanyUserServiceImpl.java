/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import com.edgarsannic.dealergestor.domain.model.CompanyUser;
import com.edgarsannic.dealergestor.domain.repository.CompanyUserRepository;
import com.edgarsannic.dealergestor.utils.ModelMapperUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyUserServiceImpl implements CompanyUserService{

    private final CompanyUserRepository companyUserRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final PasswordEncoder passwordEncoder;

    public CompanyUserServiceImpl(CompanyUserRepository companyUserRepository,
                                  ModelMapperUtil modelMapperUtil,
                                  PasswordEncoder passwordEncoder) {
        this.companyUserRepository = companyUserRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<CompanyUser> findAllCompanyUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CompanyUserEntity currentUser = (CompanyUserEntity) authentication.getPrincipal();

        return companyUserRepository.findAll().stream()
                .filter(user -> currentUser.getRole() == CompanyUserEntity.Role.SUPER_ADMIN || user.getRole() != CompanyUserEntity.Role.SUPER_ADMIN)
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyUser findCompanyUserById(Long id) {
        CompanyUserEntity requested = companyUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanyUser not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CompanyUserEntity currentUser = (CompanyUserEntity) authentication.getPrincipal();

        if (requested.getRole() == CompanyUserEntity.Role.SUPER_ADMIN){
            throw new RuntimeException("CompanyUser not found");
        }

        return modelMapperUtil.toModel(requested);
    }

    @Override
    public CompanyUser saveCompanyUser(CompanyUser companyUser) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CompanyUserEntity currentUser = (CompanyUserEntity) authentication.getPrincipal();

        CompanyUserEntity.Role roleToCreate = CompanyUserEntity.Role.valueOf(companyUser.getRole());

        if (roleToCreate == CompanyUserEntity.Role.SUPER_ADMIN &&
                currentUser.getRole() != CompanyUserEntity.Role.SUPER_ADMIN) {
            throw new RuntimeException("CompanyUser not found");
        }

        CompanyUserEntity entity = new CompanyUserEntity();
        entity.setUsername(companyUser.getUsername());
        entity.setPassword(passwordEncoder.encode(companyUser.getPassword()));
        entity.setRole(roleToCreate);
        entity.setEnabled(true);

        return modelMapperUtil.toModel(companyUserRepository.save(entity));
    }

    @Override
    public CompanyUser updateCompanyUser(Long id, CompanyUser companyUser) {
        CompanyUserEntity entity = companyUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CompanyUser not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CompanyUserEntity currentUser = (CompanyUserEntity) authentication.getPrincipal();

        if (entity.getRole() == CompanyUserEntity.Role.SUPER_ADMIN &&
                currentUser.getRole() != CompanyUserEntity.Role.SUPER_ADMIN) {
            throw new RuntimeException("CompanyUser not found");
        }

        entity.setUsername(companyUser.getUsername());

        if (companyUser.getPassword() != null && !companyUser.getPassword().isBlank()) {
            entity.setPassword(passwordEncoder.encode(companyUser.getPassword()));
        }

        entity.setRole(CompanyUserEntity.Role.valueOf(companyUser.getRole()));
        entity.setEnabled(companyUser.isEnabled());

        return modelMapperUtil.toModel(companyUserRepository.save(entity));
    }


    @Override
    public void deleteCompanyUser(Long id) {
        companyUserRepository.findById(id).ifPresent(user -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CompanyUserEntity currentUser = (CompanyUserEntity) authentication.getPrincipal();

            if (user.getRole() != CompanyUserEntity.Role.SUPER_ADMIN ||
                    currentUser.getRole() == CompanyUserEntity.Role.SUPER_ADMIN) {
                companyUserRepository.deleteById(id);
            }
        });
    }

    @Override
    public CompanyUser findByUsername(String username) {
        return modelMapperUtil.toModel(companyUserRepository.findByUsername(username));
    }
}