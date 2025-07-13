/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.controller.ViewModel.AppointmentPostViewModel;
import com.edgarsannic.dealergestor.controller.ViewModel.AppointmentViewModel;
import com.edgarsannic.dealergestor.domain.model.Appointment;
import com.edgarsannic.dealergestor.domain.model.Vehicle;
import com.edgarsannic.dealergestor.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointments", description = "Endpoints for managing appointments")
public class AppointmentController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public AppointmentController(DealerGestorManager dealerGestorManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointments retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    @ResponseBody
    public List<AppointmentViewModel> findAllAppointments() {
        return dealerGestorManager.findAllAppointments()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get today's appointments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Today's appointments retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/now")
    @ResponseBody
    public List<AppointmentViewModel> findNowAppointments() {
        return dealerGestorManager.findNowAppointments()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment found successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    @ResponseBody
    public AppointmentViewModel findAppointmentById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorManager.findAppointmentById(id));
    }

    @Operation(summary = "Create a new appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid appointment data")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public AppointmentViewModel saveAppointment(@RequestBody AppointmentPostViewModel appointmentPostViewModel) {

        Vehicle vehicle = dealerGestorManager.findVehicleById(appointmentPostViewModel.getVehicleId());

        Appointment appointment = new Appointment();
        appointment.setTask(appointmentPostViewModel.getTask());
        appointment.setDateTime(appointmentPostViewModel.getDateTime());
        appointment.setVehicle(vehicle);

        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.saveAppointment(appointment)
        );
    }

    @Operation(summary = "Update an existing appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment updated successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public AppointmentViewModel updateAppointment(@PathVariable Long id, @RequestBody AppointmentPostViewModel updatedAppointment) {

        Vehicle vehicle = dealerGestorManager.findVehicleById(updatedAppointment.getVehicleId());

        Appointment appointment = new Appointment();
        appointment.setTask(updatedAppointment.getTask());
        appointment.setDateTime(updatedAppointment.getDateTime());
        appointment.setVehicle(vehicle);
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.updateAppointment(id, appointment)
        );
    }

    @Operation(summary = "Delete an appointment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Appointment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Appointment not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {
        dealerGestorManager.deleteAppointment(id);
        return ResponseEntity.ok().build();
    }
}