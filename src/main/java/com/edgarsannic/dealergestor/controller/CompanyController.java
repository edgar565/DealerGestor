/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.controller;

import com.edgarsannic.dealergestor.DealerGestorManager;
import com.edgarsannic.dealergestor.utils.ViewModelMapperUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@Tag(name = "Company", description = "Endpoints for company branding")
public class CompanyController {

    private final DealerGestorManager dealerGestorManager;
    private final ViewModelMapperUtil viewModelMapperUtil;

    public CompanyController(DealerGestorManager dealerGestorManager,
                                          ViewModelMapperUtil viewModelMapperUtil) {
        this.dealerGestorManager = dealerGestorManager;
        this.viewModelMapperUtil = viewModelMapperUtil;
    }


}