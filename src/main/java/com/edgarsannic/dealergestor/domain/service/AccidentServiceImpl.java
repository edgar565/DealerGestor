/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.domain.service;

import com.edgarsannic.dealergestor.domain.entity.*;
import com.edgarsannic.dealergestor.domain.model.Accident;
import com.edgarsannic.dealergestor.domain.repository.*;
import com.edgarsannic.dealergestor.utils.ModelMapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccidentServiceImpl implements AccidentService {

    private final AccidentRepository accidentRepository;
    private final VehicleRepository vehicleRepository;
    private final ModelMapperUtil modelMapperUtil;
    private final CompanyUserRepository companyUserRepository;
    private final RepairRepository repairRepository;
    private final AppointmentRepository appointmentRepository;


    public AccidentServiceImpl(AccidentRepository accidentRepository, VehicleRepository vehicleRepository, ModelMapperUtil modelMapperUtil, CompanyUserRepository companyUserRepository, RepairRepository repairRepository, AppointmentRepository appointmentRepository) {
        this.accidentRepository = accidentRepository;
        this.vehicleRepository = vehicleRepository;
        this.modelMapperUtil = modelMapperUtil;
        this.companyUserRepository = companyUserRepository;
        this.repairRepository = repairRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Accident> findAllAccidents() {
        return accidentRepository.findAll().stream()
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<Accident> findAllAccidentsActive() {
        return accidentRepository.findAll().stream()
                .filter(AccidentEntity::isActive) // solo activos
                .map(modelMapperUtil::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public Accident findAccidentById(Long id) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .filter(AccidentEntity::isActive)
                .orElseThrow(() -> new RuntimeException("Accident not found"));
        return modelMapperUtil.toModel(accidentEntity);
    }

    @Override
    public Accident saveAccident(Accident model) {
        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        CompanyUserEntity companyUserEntity = companyUserRepository.findById(model.getOperator().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Company user not found"));

        boolean hasRepair = repairRepository.existsByVehicle(vehicleEntity);
        boolean hasAppointment = appointmentRepository.existsByVehicle(vehicleEntity);
        boolean hasAccident = accidentRepository.existsByVehicle(vehicleEntity);


        if (hasRepair || hasAppointment || hasAccident) {
            throw new RuntimeException("This vehicle already has an active appointment, repair, or accident.");
        }

        AccidentEntity accidentEntity = new AccidentEntity();
        accidentEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus()));
        accidentEntity.setDate(model.getDate());
        accidentEntity.setActive(true);
        accidentEntity.setVehicle(vehicleEntity);
        accidentEntity.setOperator(companyUserEntity);
        accidentEntity.setInsuranceCompany(model.getInsuranceCompany());
        accidentEntity.setLocation(model.getLocation());

        return modelMapperUtil.toModel(accidentRepository.save(accidentEntity));
    }

    @Override
    public Accident updateAccident(Long id, Accident model) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));

        VehicleEntity vehicleEntity = vehicleRepository.findById(model.getVehicle().getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        CompanyUserEntity companyUserEntity = companyUserRepository.findById(model.getOperator().getCompanyUserId())
                .orElseThrow(() -> new RuntimeException("Company user not found"));

        accidentEntity.setStatus(RepairEntity.Status.valueOf(model.getStatus()));
        accidentEntity.setDate(model.getDate());
        accidentEntity.setVehicle(vehicleEntity);
        accidentEntity.setOperator(companyUserEntity);
        accidentEntity.setInsuranceCompany(model.getInsuranceCompany());
        accidentEntity.setLocation(model.getLocation());

        return modelMapperUtil.toModel(accidentRepository.save(accidentEntity));
    }

    @Override
    public void deleteAccident(Long id) {
        AccidentEntity accidentEntity = accidentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Accident not found"));

        accidentEntity.setActive(false);
        accidentRepository.save(accidentEntity);
    }
}