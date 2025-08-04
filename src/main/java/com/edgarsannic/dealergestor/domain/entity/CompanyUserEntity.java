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

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "user")
public class CompanyUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long companyUserId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    public enum Role {
        SUPER_ADMIN,
        ADMIN,
        MECHANIC,
        RECEPTIONIST,
    }

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "hidden", nullable = false)
    private boolean hidden;

    @OneToMany(mappedBy = "companyUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<NoteEntity> noteEntities;

    @OneToMany(mappedBy = "companyUserEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairEntity> repairEntities;

    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id", nullable = true)
    @JsonIgnore
    private CompanyEntity companyEntity;

    @OneToMany(mappedBy = "sender")
    private List<MessageEntity> sentMessages;

    @OneToMany(mappedBy = "receiver")
    private List<MessageEntity> receivedMessages;
}