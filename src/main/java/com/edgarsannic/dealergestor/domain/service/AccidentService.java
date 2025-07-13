/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.Accident;

import java.util.List;

public interface AccidentService {

    List<Accident> findAllAccidents();
    public List<Accident> findAllAccidentsActive();
    Accident findAccidentById(Long id);
    Accident saveAccident(Accident request);
    Accident updateAccident(Long id, Accident request);
    void deleteAccident(Long id);
}