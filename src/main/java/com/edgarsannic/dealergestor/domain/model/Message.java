/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.model;

import com.edgarsannic.dealergestor.domain.entity.CompanyUserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Message {

    private Long messageId;
    private CompanyUserEntity sender;
    private CompanyUserEntity receiver;
    private String content;
    private LocalDateTime sentAt;
    private boolean isRead;
}