/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.utils;

import com.edgarsannic.dealergestor.domain.entity.*;
import com.edgarsannic.dealergestor.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class ModelMapperUtil {

    public Client toModel(ClientEntity clientEntity) {
        Client model = new Client();
        model.setClientId(clientEntity.getClientId());
        model.setName(clientEntity.getName());
        model.setPhone(clientEntity.getPhone());
        model.setVehicles(clientEntity.getVehicleEntities()
                .stream().map(this::toModel)
                .collect(Collectors.toList()));
        return model;
    }

    public Vehicle toModel(VehicleEntity vehicleEntity) {
        Vehicle model = new Vehicle();
        model.setVehicleId(vehicleEntity.getVehicleId());
        model.setLicensePlate(vehicleEntity.getLicensePlate());
        model.setBrand(vehicleEntity.getBrand());
        model.setModel(vehicleEntity.getModel());
        model.setRepairs(vehicleEntity.getRepairs()
                .stream().map(this::toModel)
                .collect(Collectors.toList()));
        model.setAppointment(vehicleEntity.getAppointmentEntity().stream()
                .findFirst()
                .map(this::toModel).orElse(null));
        return model;
    }

    public Appointment toModel(AppointmentEntity appointmentEntity) {
        Appointment model = new Appointment();
        model.setAppointmentId(appointmentEntity.getAppointmentId());
        model.setDateTime(appointmentEntity.getDateTime());
        model.setTask(appointmentEntity.getTask().toString());
        return model;
    }

    public Repair toModel(RepairEntity repairEntity) {
        Repair model = new Repair();
        model.setRepairId(repairEntity.getRepairId());
        model.setStatus(repairEntity.getStatus().toString());
        model.setDate(repairEntity.getDate());
        model.setPart(repairEntity.getPartEntity().stream().map(this::toModel).toList());
        model.setOperator(toModel(repairEntity.getOperator()));
        return model;
    }

    public Accident toModel(AccidentEntity accidentEntity) {
        Accident model = new Accident();
        model.setRepairId(accidentEntity.getRepairId());
        model.setStatus(accidentEntity.getStatus().toString());
        model.setDate(accidentEntity.getDate());
        model.setOperator(toModel(accidentEntity.getOperator()));
        model.setPart(accidentEntity.getPartEntity().stream().map(this::toModel).toList());
        model.setInsuranceCompany(accidentEntity.getInsuranceCompany());
        model.setLocation(accidentEntity.getLocation());
        return model;
    }

    public Part toModel(PartEntity partEntity) {
        Part model = new Part();
        model.setPartId(partEntity.getPartId());
        model.setKeychain(partEntity.getKeychain());
        model.setNumberOrder(partEntity.getNumberOrder());
        model.setWork(partEntity.getWork());
        model.setMaterials(partEntity.getMaterials());
        model.setObservations(partEntity.getObservations());
        model.setLights(partEntity.isLights());
        model.setBrakeSensors(partEntity.isBrakeSensors());
        model.setCableTension(partEntity.isCableTension());
        model.setTirePressure(partEntity.isTirePressure());
        model.setEngineOil(partEntity.isEngineOil());
        model.setWear(partEntity.isWear());
        model.setBrakeFluid(partEntity.isBrakeFluid());
        model.setBrakePads(partEntity.isBrakePads());
        model.setTransmissionKit(partEntity.isTransmissionKit());
        model.setSteeringCondition(partEntity.isSteeringCondition());
        model.setDynamicTest(partEntity.isDynamicTest());
        model.setDay1(partEntity.getDay1());
        model.setDay2(partEntity.getDay2());
        model.setDay3(partEntity.getDay3());
        model.setDay4(partEntity.getDay4());
        model.setDay5(partEntity.getDay5());
        model.setDay6(partEntity.getDay6());
        model.setTime1(partEntity.getTime1());
        model.setTime2(partEntity.getTime2());
        model.setTime3(partEntity.getTime3());
        model.setTime4(partEntity.getTime4());
        model.setTime5(partEntity.getTime5());
        model.setTime6(partEntity.getTime6());
        model.setTime7(partEntity.getTime7());
        model.setTime8(partEntity.getTime8());
        model.setTime9(partEntity.getTime9());
        model.setTime10(partEntity.getTime10());
        model.setTime11(partEntity.getTime11());
        model.setTime12(partEntity.getTime12());
        return model;
    }

    public CompanyUser toModel(CompanyUserEntity companyUserEntity) {
        if (companyUserEntity == null) {
            return null;
        }
        CompanyUser model = new CompanyUser();
        model.setCompanyUserId(companyUserEntity.getCompanyUserId());
        model.setUsername(companyUserEntity.getUsername());
        model.setPassword(companyUserEntity.getPassword());
        model.setRole(companyUserEntity.getRole().name());
        model.setEnabled(companyUserEntity.isEnabled());
        model.setNotes(
                companyUserEntity.getNoteEntities() != null
                        ? companyUserEntity.getNoteEntities()
                        .stream()
                        .map(this::toModel)
                        .collect(Collectors.toList())
                        : new ArrayList<>()
        );
        return model;
    }

    public Note toModel(NoteEntity noteEntity) {
        Note model = new Note();
        model.setNoteId(noteEntity.getNoteId());
        model.setTitle(noteEntity.getTitle());
        model.setContent(noteEntity.getContent());
        model.setCreatedAt(noteEntity.getCreatedAt());
        return model;
    }

    public CompanyConfiguration toModel(CompanyConfigurationEntity companyConfigurationEntity) {
        CompanyConfiguration model = new CompanyConfiguration();
        model.setCompanyId(companyConfigurationEntity.getCompanyId());
        model.setNameCompany(companyConfigurationEntity.getNameCompany());
        model.setPrimaryColor(companyConfigurationEntity.getPrimaryColor());
        model.setSecondaryColor(companyConfigurationEntity.getSecondaryColor());
        model.setLogoPath(companyConfigurationEntity.getLogoPath());
        model.setWhatsappApiKey(companyConfigurationEntity.getWhatsappApiKey());
        return model;
    }
}