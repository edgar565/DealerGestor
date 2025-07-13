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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "company")
public class CompanyConfigurationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comapny_id")
    private Long companyId;

    @Column(nullable=false)
    private String nameCompany;

    @Column(name="primary_color")
    private String primaryColor;

    @Column(name="secondary_color")
    private String secondaryColor;

    @Column(name="logo_path")
    private String logoPath; // ruta en S3 o carpeta local

    @Column(name="whatsapp_api_key")
    private String whatsappApiKey;
}