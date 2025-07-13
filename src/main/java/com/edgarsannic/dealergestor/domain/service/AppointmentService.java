/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.model.Appointment;

import java.util.List;

public interface AppointmentService {

    List<Appointment> findAllAppointments();
    List<Appointment> findNowAppointments();
    Appointment findAppointmentById(Long id);
    Appointment saveAppointment(Appointment request);
    Appointment updateAppointment(Long id, Appointment request);
    void deleteAppointment(Long id);
}