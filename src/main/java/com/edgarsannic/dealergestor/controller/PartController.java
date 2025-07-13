package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.controller.ViewModel.PartPostViewModel;
import com.edgarsannic.dealergestor.controller.ViewModel.PartViewModel;
import com.edgarsannic.dealergestor.domain.model.Part;
import com.edgarsannic.dealergestor.domain.model.Repair;
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
@RequestMapping("/parts")
@Tag(name = "Parts", description = "Endpoints for managing parts")
public class PartController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public PartController(DealerGestorManager dealerGestorManager, ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all parts", description = "Retrieve a list of all parts")
    @ApiResponse(responseCode = "200", description = "Parts retrieved successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @GetMapping
    public List<PartViewModel> findAllParts() {
        return dealerGestorManager.findAllParts()
                .stream().map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get a part by ID", description = "Retrieve a specific part by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Part retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST', 'MECHANIC')")
    @GetMapping("/{id}")
    public PartViewModel findPartById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorManager.findPartById(id));
    }

    @Operation(summary = "Create a new part", description = "Save a new part in the system")
    @ApiResponse(responseCode = "201", description = "Part created successfully")
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @PostMapping("/save")
    public PartViewModel savePart(@RequestBody PartPostViewModel partPostViewModel) {

        Repair repair = dealerGestorManager.findRepairById(partPostViewModel.getRepairId());

        Part part = new Part();
        part.setKeychain(partPostViewModel.getKeychain());
        part.setNumberOrder(partPostViewModel.getNumberOrder());
        part.setWork(partPostViewModel.getWork());
        part.setMaterials(partPostViewModel.getMaterials());
        part.setRepair(repair);

        return viewModelMapperUtil.toViewModel(dealerGestorManager.savePart(part));
    }

    @Operation(summary = "Update a part", description = "Update an existing part by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Part updated successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST', 'MECHANIC')")
    @PutMapping("/updateRepair/{id}")
    public PartViewModel updatePart(@PathVariable Long id, @RequestBody PartViewModel updatedPart) {

        Repair repair = dealerGestorManager.findRepairById(updatedPart.getRepairId());
        Part part = viewModelMapperUtil.toModel(updatedPart);

        part.setRepair(repair);

        return viewModelMapperUtil.toViewModel(dealerGestorManager.updatePart(id, part));
    }

    @Operation(summary = "Delete a part", description = "Delete a part by its ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Part deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Part not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN', 'RECEPTIONIST')")
    @DeleteMapping("/deleteRepair/{id}")
    public ResponseEntity<?> deletePart(@PathVariable Long id) {
        dealerGestorManager.deletePart(id);
        return ResponseEntity.ok().build();
    }
}