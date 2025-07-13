package com.edgarsannic.dealergestor.utils;

import com.edgarsannic.dealergestor.domain.entity.*;
import com.edgarsannic.dealergestor.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapperUtil {

    public VehicleEntity toEntity(Vehicle model) {
        if (model == null) {
            return null;
        }

        VehicleEntity entity = new VehicleEntity();
        entity.setVehicleId(model.getVehicleId());
        entity.setLicensePlate(model.getLicensePlate());
        entity.setBrand(model.getBrand());
        entity.setModel(model.getModel());

        if (model.getClient() != null) {
            entity.setClientEntity(toClientEntity(model.getClient()));
        }

        if (model.getRepairs() != null) {
            entity.setRepairs(model.getRepairs().stream()
                    .map(this::toRepairEntity)
                    .collect(Collectors.toList()));
        }

        if (model.getAppointment() != null) {
            List<AppointmentEntity> appointmentEntities = new ArrayList<>();
            appointmentEntities.add(toAppointmentEntity(model.getAppointment()));
            entity.setAppointmentEntity(appointmentEntities);
        }

        return entity;
    }

    public AppointmentEntity toAppointmentEntity(Appointment model) {
        if (model == null) {
            return null;
        }

        AppointmentEntity entity = new AppointmentEntity();
        entity.setAppointmentId(model.getAppointmentId());
        entity.setDateTime(model.getDateTime());
        entity.setTask(AppointmentEntity.Task.valueOf(model.getTask()));

        return entity;
    }

    public ClientEntity toClientEntity(Client model) {
        if (model == null) {
            return null;
        }

        ClientEntity entity = new ClientEntity();
        entity.setClientId(model.getClientId());
        entity.setName(model.getName());
        entity.setPhone(model.getPhone());

        return entity;
    }

    public RepairEntity toRepairEntity(Repair model) {
        if (model == null) {
            return null;
        }

        RepairEntity entity = new RepairEntity();
        entity.setRepairId(model.getRepairId());
        entity.setStatus(RepairEntity.Status.valueOf(model.getStatus()));
        entity.setDate(model.getDate());
        entity.setActive(model.isActive());

        if (model.getVehicle() != null) {
            entity.setVehicle(toVehicleEntity(model.getVehicle()));
        }

        if (model.getPart() != null) {
            entity.setPartEntity(model.getPart().stream().map(this::toPartEntity).toList());
        }

        if (model.getOperator() != null) {
            entity.setOperator(toCompanyUserEntity(model.getOperator()));
        }

        return entity;
    }

    public AccidentEntity toAccidentEntity(Accident model) {
        if (model == null) {
            return null;
        }

        AccidentEntity entity = new AccidentEntity();
        entity.setRepairId(model.getRepairId());
        entity.setStatus(RepairEntity.Status.valueOf(model.getStatus()));
        entity.setDate(model.getDate());
        entity.setActive(model.isActive());
        entity.setInsuranceCompany(model.getInsuranceCompany());
        entity.setLocation(model.getLocation());

        if (model.getVehicle() != null) {
            entity.setVehicle(toVehicleEntity(model.getVehicle()));
        }

        if (model.getPart() != null) {
            entity.setPartEntity(model.getPart().stream().map(this::toPartEntity).toList());
        }

        if (model.getOperator() != null) {
            entity.setOperator(toCompanyUserEntity(model.getOperator()));
        }

        return entity;
    }

    public PartEntity toPartEntity(Part model) {
        if (model == null) {
            return null;
        }

        PartEntity entity = new PartEntity();
        entity.setPartId(model.getPartId());
        entity.setKeychain(model.getKeychain());
        entity.setNumberOrder(model.getNumberOrder());
        entity.setWork(model.getWork());
        entity.setMaterials(model.getMaterials());
        entity.setObservations(model.getObservations());
        entity.setLights(model.isLights());
        entity.setBrakeSensors(model.isBrakeSensors());
        entity.setCableTension(model.isCableTension());
        entity.setTirePressure(model.isTirePressure());
        entity.setEngineOil(model.isEngineOil());
        entity.setWear(model.isWear());
        entity.setBrakeFluid(model.isBrakeFluid());
        entity.setBrakePads(model.isBrakePads());
        entity.setTransmissionKit(model.isTransmissionKit());
        entity.setSteeringCondition(model.isSteeringCondition());
        entity.setDynamicTest(model.isDynamicTest());
        entity.setDay1(model.getDay1());
        entity.setDay2(model.getDay2());
        entity.setDay3(model.getDay3());
        entity.setDay4(model.getDay4());
        entity.setDay5(model.getDay5());
        entity.setDay6(model.getDay6());
        entity.setTime1(model.getTime1());
        entity.setTime2(model.getTime2());
        entity.setTime3(model.getTime3());
        entity.setTime4(model.getTime4());
        entity.setTime5(model.getTime5());
        entity.setTime6(model.getTime6());
        entity.setTime7(model.getTime7());
        entity.setTime8(model.getTime8());
        entity.setTime9(model.getTime9());
        entity.setTime10(model.getTime10());
        entity.setTime11(model.getTime11());
        entity.setTime12(model.getTime12());

        if (model.getRepair() != null) {
            entity.setRepairEntity(toRepairEntity(model.getRepair()));
        }

        return entity;
    }

    public VehicleEntity toVehicleEntity(Vehicle model) {
        if (model == null) {
            return null;
        }

        VehicleEntity entity = new VehicleEntity();
        entity.setVehicleId(model.getVehicleId());
        entity.setLicensePlate(model.getLicensePlate());
        entity.setBrand(model.getBrand());
        entity.setModel(model.getModel());

        if (model.getClient() != null) {
            entity.setClientEntity(toClientEntity(model.getClient()));
        }

        return entity;
    }

    public CompanyUserEntity toCompanyUserEntity(CompanyUser model) {
        if (model == null) {
            return null;
        }

        CompanyUserEntity entity = new CompanyUserEntity();
        entity.setCompanyUserId(model.getCompanyUserId());
        entity.setUsername(model.getUsername());
        entity.setPassword(model.getPassword());
        entity.setRole(CompanyUserEntity.Role.valueOf(model.getRole()));
        entity.setEnabled(model.isEnabled());

        return entity;
    }
}
