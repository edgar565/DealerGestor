/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(nullable=false)
    private String nameCompany;

    @OneToMany(mappedBy = "companyUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompanyUserEntity> companyUserEntities;

    @OneToOne
    @JoinColumn(name = "company_configuration_id")
    private CompanyConfigurationEntity companyConfiguration;
}