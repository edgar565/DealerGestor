/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.Part;

import java.util.List;

public interface PartService {

    List<Part> findAllParts();
    Part findPartById(Long id);
    Part savePart(Part request);
    Part updatePart(Long id, Part request);
    void deletePart(Long id);
}