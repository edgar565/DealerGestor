/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Part {

    private Long partId;
    private String keychain;
    private String numberOrder;
    private String work;
    private String materials;
    private String observations;
    private boolean lights;
    private boolean brakeSensors;
    private boolean cableTension;
    private boolean tirePressure;
    private boolean engineOil;
    private boolean wear;
    private boolean brakeFluid;
    private boolean brakePads;
    private boolean transmissionKit;
    private boolean steeringCondition;
    private boolean dynamicTest;
    private LocalDate day1;
    private LocalDate day2;
    private LocalDate day3;
    private LocalDate day4;
    private LocalDate day5;
    private LocalDate day6;
    private LocalTime time1;
    private LocalTime time2;
    private LocalTime time3;
    private LocalTime time4;
    private LocalTime time5;
    private LocalTime time6;
    private LocalTime time7;
    private LocalTime time8;
    private LocalTime time9;
    private LocalTime time10;
    private LocalTime time11;
    private LocalTime time12;
    private Repair repair;
}