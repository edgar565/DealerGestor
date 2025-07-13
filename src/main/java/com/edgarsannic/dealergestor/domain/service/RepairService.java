/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.Repair;

import java.util.List;

public interface RepairService {

    List<Repair> findAllRepairs();
    public List<Repair> findAllRepairsActive();
    Repair findRepairById(Long id);
    Repair saveRepair(Repair request);
    Repair updateRepair(Long id, Repair request);
    void deleteRepair(Long id);
}