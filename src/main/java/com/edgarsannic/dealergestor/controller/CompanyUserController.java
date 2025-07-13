/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.controller.ViewModel.CompanyUserPostViewModel;
import com.edgarsannic.dealergestor.controller.ViewModel.CompanyUserViewModel;
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
@RequestMapping("/company-users")
@Tag(name = "Company Users", description = "Endpoints for managing users within a company")
public class CompanyUserController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public CompanyUserController(DealerGestorManager dealerGestorManager,
                                 ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }

    @Operation(summary = "Get all company users", description = "Retrieves a list of all users registered within the company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping
    @ResponseBody
    public List<CompanyUserViewModel> findAllCompanyUsers() {
        return dealerGestorManager.findAllCompanyUsers().stream()
                .map(viewModelMapperUtil::toViewModel)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get company user by ID", description = "Retrieves a specific company user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @GetMapping("/{id}")
    @ResponseBody
    public CompanyUserViewModel findCompanyUserById(@PathVariable Long id) {
        return viewModelMapperUtil.toViewModel(dealerGestorManager.findCompanyUserById(id));
    }

    @Operation(summary = "Register a new company user", description = "Creates a new user for the company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PostMapping("/register")
    public CompanyUserViewModel saveCompanyUser(@RequestBody CompanyUserPostViewModel companyUserPostViewModel) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.saveCompanyUser(viewModelMapperUtil.toModel(companyUserPostViewModel))
        );
    }

    @Operation(summary = "Update a company user", description = "Updates user information based on the given ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @PutMapping("/update/{id}")
    public CompanyUserViewModel updateCompanyUser(@PathVariable Long id, @RequestBody CompanyUserPostViewModel companyUserPostViewModel) {
        return viewModelMapperUtil.toViewModel(
                dealerGestorManager.updateCompanyUser(id, viewModelMapperUtil.toModel(companyUserPostViewModel))
        );
    }

    @Operation(summary = "Delete a company user", description = "Deletes a company user by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'SUPER_ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCompanyUser(@PathVariable Long id) {
        dealerGestorManager.deleteCompanyUser(id);
        return ResponseEntity.ok().build();
    }
}