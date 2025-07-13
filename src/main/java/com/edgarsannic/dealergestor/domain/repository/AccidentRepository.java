/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.repository;

import com.edgarsannic.dealergestor.domain.entity.AccidentEntity;
import com.edgarsannic.dealergestor.domain.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentRepository extends JpaRepository<AccidentEntity, Long> {

    boolean existsByVehicle(VehicleEntity vehicleEntity);
}