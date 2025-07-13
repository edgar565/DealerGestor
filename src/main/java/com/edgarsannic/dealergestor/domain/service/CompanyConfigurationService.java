/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.CompanyConfiguration;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyConfigurationService {

    CompanyConfiguration findCompanyConfiguration(Long id);
    CompanyConfiguration updateCompanyConfiguration(CompanyConfiguration companyConfiguration, MultipartFile logoFile);
}