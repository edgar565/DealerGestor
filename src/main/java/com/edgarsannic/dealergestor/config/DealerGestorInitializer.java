/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.config;

import com.edgarsannic.dealergestor.domain.entity.CompanyConfigurationEntity;
import com.edgarsannic.dealergestor.domain.entity.CompanyEntity;
import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import com.edgarsannic.dealergestor.domain.repository.CompanyConfigurationRepository;
import com.edgarsannic.dealergestor.domain.repository.CompanyRepository;
import com.edgarsannic.dealergestor.domain.repository.CompanyUserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DealerGestorInitializer {

    private final CompanyConfigurationRepository configRepository;
    private final CompanyRepository companyRepository;
    private final CompanyUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DealerGestorInitializer(CompanyConfigurationRepository configRepository,
                          CompanyRepository companyRepository,
                          CompanyUserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.configRepository = configRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        // Si ya existe la empresa principal, no hacemos nada
        if (companyRepository.findByNameCompany("MaviMotos") != null) return;

        // 1. Crear configuración de la empresa
        CompanyConfigurationEntity config = new CompanyConfigurationEntity();
        config.setPrimaryColor("#BD1522");
        config.setSecondaryColor("#6f6f6f");
        config.setLogoPath("logo_sin_fondo.svg");
        config.setWhatsappApiKey("fake-whatsapp-api-key");
        config = configRepository.save(config);

        // 2. Crear empresa
        CompanyEntity company = new CompanyEntity();
        company.setNameCompany("MaviMotos");
        company.setCompanyConfiguration(config);
        company = companyRepository.save(company);

        // 3. Crear superadmin
        CompanyUserEntity superAdmin = new CompanyUserEntity();
        superAdmin.setUsername("dealergestor");
        superAdmin.setPassword(passwordEncoder.encode("admin123"));
        superAdmin.setRole(CompanyUserEntity.Role.SUPER_ADMIN);
        superAdmin.setEnabled(true);
        superAdmin.setCompanyEntity(company);
        userRepository.save(superAdmin);

        System.out.println("✅ Empresa, configuración y superadmin inicializados correctamente.");
    }
}