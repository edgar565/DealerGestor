/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.repository;

import com.edgarsannic.dealergestor.domain.entity.CompanyConfigurationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyConfigurationRepository extends JpaRepository<CompanyConfigurationEntity, Long> {

}