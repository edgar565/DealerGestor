/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.controller.ViewModel.AccidentPostViewModel;
import com.edgarsannic.dealergestor.controller.ViewModel.AccidentViewModel;
import com.edgarsannic.dealergestor.domain.model.Accident;
import com.edgarsannic.dealergestor.domain.model.CompanyUser;
import com.edgarsannic.dealergestor.domain.model.Vehicle;
import com.edgarsannic.dealergestor.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/accidents")
@Tag(name = "Accidents", description = "Endpoints for managing accidents")
public class AccidentController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public AccidentController(DealerGestorManager dealerGestorManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all accidents (active and inactive)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All accidents retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/all")
    @ResponseBody
    public List<AccidentViewModel> findAllAccidents() {
        return dealerGestorManager.findAllAccidents()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get all active accidents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Active accidents retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    @ResponseBody
    public List<AccidentViewModel> findAllAccidentsActive() {
        return dealerGestorManager.findAllAccidentsActive()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Find accident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Accident not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    @ResponseBody
    public AccidentViewModel findAccidentById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorManager.findAccidentById(id));
    }

    @Operation(summary = "Create a new accident")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public AccidentViewModel saveAccident(@RequestBody AccidentPostViewModel accidentPostViewModel) {

        CompanyUser companyUser = dealerGestorManager.findCompanyUserById(accidentPostViewModel.getOperatorId());
        Vehicle vehicle = dealerGestorManager.findVehicleById(accidentPostViewModel.getVehicleId());

        Accident accident = new Accident();
        accident.setStatus(accidentPostViewModel.getStatus());
        accident.setDate(LocalDate.now());
        accident.setOperator(companyUser);
        accident.setVehicle(vehicle);
        accident.setActive(true);
        accident.setLocation(accidentPostViewModel.getLocation());
        accident.setInsuranceCompany(accidentPostViewModel.getInsuranceCompany());
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.saveAccident(accident)
        );
    }

    @Operation(summary = "Update an existing accident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident updated successfully"),
            @ApiResponse(responseCode = "404", description = "Accident not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public AccidentViewModel updateAccident(@PathVariable Long id, @RequestBody AccidentPostViewModel updatedAccident) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.updateAccident(id, viewModelMapperUtil.toModel(updatedAccident))
        );
    }

    @Operation(summary = "Delete an accident by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Accident deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Accident not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAccident(@PathVariable Long id) {
        dealerGestorManager.deleteAccident(id);
        return ResponseEntity.ok().build();
    }
}