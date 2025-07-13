/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "repair")
public class RepairEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repair_id")
    private Long repairId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    public enum Status {
        EN_REPARACION {
            @Override
            public String toString() {
                return "En reparación";
            }
        },
        PEDIDO_RECAMBIO {
            @Override
            public String toString() {
                return "Pedido recambio";
            }
        },
        FINALIZADA_CONTACTO {
            @Override
            public String toString() {
                return "Finalizada con contacto";
            }
        },
        FINALIZADA {
            @Override
            public String toString() {
                return "Finalizada";
            }
        },
        CIERRE {
            @Override
            public String toString() {
                return "Cierre";
            }
        }
    }

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "isActive")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @JsonIgnore
    private VehicleEntity vehicle;

    @OneToMany(mappedBy = "repairEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartEntity> partEntity = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "operator_id")
    private CompanyUserEntity operator;
}