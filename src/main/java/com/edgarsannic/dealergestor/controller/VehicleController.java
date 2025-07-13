package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.controller.ViewModel.VehiclePostViewModel;
import com.edgarsannic.dealergestor.controller.ViewModel.VehicleViewModel;
import com.edgarsannic.dealergestor.domain.model.Client;
import com.edgarsannic.dealergestor.domain.model.Vehicle;
import com.edgarsannic.dealergestor.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Vehicles", description = "API endpoints for managing vehicles")
@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public VehicleController(DealerGestorManager dealerGestorManager,
                             ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all vehicles", description = "Returns a list of all vehicles.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of vehicles retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicles not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    public List<VehicleViewModel> findAllVehicles() {
        return dealerGestorManager.findAllVehicles()
                .stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get vehicle by ID", description = "Returns a single vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/{id}")
    public VehicleViewModel findVehicleById(
            @Parameter(description = "ID of the vehicle to retrieve", required = true)
            @PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.findVehicleById(id)
        );
    }

    @Operation(summary = "Get vehicle by license plate", description = "Returns a single vehicle by its license plate.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping("/licensePlate/{licensePlate}")
    public VehicleViewModel findVehicleByLicensePlate(
            @Parameter(description = "License plate of the vehicle to retrieve", required = true)
            @PathVariable String licensePlate) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.findVehicleByLicensePlate(licensePlate)
        );
    }

    @Operation(summary = "Create a new vehicle", description = "Creates a new vehicle record.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vehicle created successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public ResponseEntity<VehicleViewModel> saveVehicle(
            @Parameter(description = "Datos del vehículo con su cliente", required = true,
                    content = @Content(schema = @Schema(implementation = VehiclePostViewModel.class)))
            @Valid @RequestBody VehiclePostViewModel vehicleViewModel) {

        System.out.println("Datos recibidos: " + vehicleViewModel);

        // Buscar el cliente existente por ID
        Client client = dealerGestorManager.findClientById(vehicleViewModel.getClientViewModelId());

        if (vehicleViewModel == null) {
            System.out.println("vehiclePostViewModel es null");
        } else {
            System.out.println("License Plate: " + vehicleViewModel.getLicensePlate());
            System.out.println("Brand: " + vehicleViewModel.getBrand());
            System.out.println("Model: " + vehicleViewModel.getModel());
            System.out.println("Client: " + client);
        }

        // Crear el vehículo y asociarlo con el cliente
        Vehicle vehicle = new Vehicle();
        vehicle.setLicensePlate(vehicleViewModel.getLicensePlate());
        vehicle.setBrand(vehicleViewModel.getBrand());
        vehicle.setModel(vehicleViewModel.getModel());
        vehicle.setClient(client);

        // Guardar el vehículo en la base de datos
        Vehicle savedVehicle = dealerGestorManager.saveVehicle(vehicle);

        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(savedVehicle));
    }


    @Operation(summary = "Update existing vehicle", description = "Updates details of an existing vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PutMapping("/update/{id}")
    public ResponseEntity<VehicleViewModel> updateVehicle(@PathVariable Long id, @RequestBody VehicleViewModel vehicleViewModel) {
        return ResponseEntity.ok(viewModelMapperUtil.toViewModel(
                dealerGestorManager.updateVehicle(id, viewModelMapperUtil.toModel(vehicleViewModel))));
    }

    @Operation(summary = "Delete a vehicle", description = "Deletes a vehicle by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehicle deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable Long id) {
        dealerGestorManager.deleteVehicle(id);
        return ResponseEntity.ok().build();
    }
}
