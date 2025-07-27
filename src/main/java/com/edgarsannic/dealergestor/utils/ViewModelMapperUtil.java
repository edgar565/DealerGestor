/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor.utils;

import com.edgarsannic.dealergestor.controller.ViewModel.*;
import com.edgarsannic.dealergestor.domain.model.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@Component
public class ViewModelMapperUtil {

    public VehicleViewModel toViewModel(Vehicle vehicle) {
        VehicleViewModel viewModel = new VehicleViewModel();
        viewModel.setVehicleId(vehicle.getVehicleId());
        viewModel.setLicensePlate(vehicle.getLicensePlate());
        viewModel.setBrand(vehicle.getBrand());
        viewModel.setModel(vehicle.getModel());
        return viewModel;
    }

    public ClientViewModel toViewModel(Client client) {
        ClientViewModel viewModel = new ClientViewModel();
        viewModel.setClientId(client.getClientId());
        viewModel.setName(client.getName());
        viewModel.setPhone(client.getPhone());
        viewModel.setVehicles(client.getVehicles()
                .stream().map(this::toViewModel)
                .collect(Collectors.toList()));
        return viewModel;
    }

    public RepairViewModel toViewModel(Repair repair) {
        RepairViewModel viewModel = new RepairViewModel();
        viewModel.setRepairId(repair.getRepairId());
        viewModel.setStatus(repair.getStatus());
        viewModel.setDate(repair.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        viewModel.setOperator(toViewModel(repair.getOperator()));
        return viewModel;
    }

    public PartViewModel toViewModel(Part part) {
        PartViewModel viewModel = new PartViewModel();
        viewModel.setPartId(part.getPartId());
        viewModel.setKeychain(part.getKeychain());
        viewModel.setNumberOrder(part.getNumberOrder());
        viewModel.setWork(part.getWork());
        viewModel.setMaterials(part.getMaterials());
        viewModel.setObservations(part.getObservations());
        viewModel.setLights(part.isLights());
        viewModel.setBrakeSensors(part.isBrakeSensors());
        viewModel.setCableTension(part.isCableTension());
        viewModel.setTirePressure(part.isTirePressure());
        viewModel.setEngineOil(part.isEngineOil());
        viewModel.setWear(part.isWear());
        viewModel.setBrakeFluid(part.isBrakeFluid());
        viewModel.setBrakePads(part.isBrakePads());
        viewModel.setTransmissionKit(part.isTransmissionKit());
        viewModel.setSteeringCondition(part.isSteeringCondition());
        viewModel.setDynamicTest(part.isDynamicTest());
        viewModel.setDay1(part.getDay1());
        viewModel.setDay2(part.getDay2());
        viewModel.setDay3(part.getDay3());
        viewModel.setDay4(part.getDay4());
        viewModel.setDay5(part.getDay5());
        viewModel.setDay6(part.getDay6());
        viewModel.setTime1(part.getTime1());
        viewModel.setTime2(part.getTime2());
        viewModel.setTime3(part.getTime3());
        viewModel.setTime4(part.getTime4());
        viewModel.setTime5(part.getTime5());
        viewModel.setTime6(part.getTime6());
        viewModel.setTime7(part.getTime7());
        viewModel.setTime8(part.getTime8());
        viewModel.setTime9(part.getTime9());
        viewModel.setTime10(part.getTime10());
        viewModel.setTime11(part.getTime11());
        viewModel.setTime12(part.getTime12());
        return viewModel;
    }

    public CompanyUserViewModel toViewModel(CompanyUser companyUser) {
        CompanyUserViewModel viewModel = new CompanyUserViewModel();
        viewModel.setCompanyUserId(companyUser.getCompanyUserId());
        viewModel.setUsername(companyUser.getUsername());
        viewModel.setRole(companyUser.getRole());
        viewModel.setEnabled(companyUser.isEnabled());
        viewModel.setNotes(companyUser.getNotes()
                .stream().map(this::toViewModel)
                .collect(Collectors.toList()));
        return viewModel;
    }

    public NoteViewModel toViewModel(Note note) {
        NoteViewModel viewModel = new NoteViewModel();
        viewModel.setNoteId(note.getNoteId());
        viewModel.setTitle(note.getTitle());
        viewModel.setContent(note.getContent());
        viewModel.setCreatedAt(note.getCreatedAt());
        return viewModel;
    }

    public AccidentViewModel toViewModel(Accident accident) {
        AccidentViewModel viewModel = new AccidentViewModel();
        viewModel.setRepairId(accident.getRepairId());
        viewModel.setStatus(accident.getStatus());
        viewModel.setDate(accident.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        viewModel.setOperator(toViewModel(accident.getOperator()));
        viewModel.setInsuranceCompany(accident.getInsuranceCompany());
        viewModel.setLocation(accident.getLocation());
        return viewModel;
    }

    public AppointmentViewModel toViewModel(Appointment appointment) {
        AppointmentViewModel viewModel = new AppointmentViewModel();
        viewModel.setAppointmentId(appointment.getAppointmentId());
        viewModel.setDateTime(appointment.getDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        viewModel.setTask(appointment.getTask());
        return viewModel;
    }

    public CompanyConfigurationViewModel toViewModel(CompanyConfiguration company) {
        CompanyConfigurationViewModel viewModel = new CompanyConfigurationViewModel();
        viewModel.setPrimaryColor(company.getPrimaryColor());
        viewModel.setSecondaryColor(company.getSecondaryColor());
        viewModel.setLogoPath(company.getLogoPath());
        viewModel.setWhatsappApiKey(company.getWhatsappApiKey());
        return viewModel;
    }


    /**
     *  THE VIEWMODEL A MODEL
     **/
    public Vehicle toModel(VehiclePostViewModel vehicleViewModel) {
        Vehicle model = new Vehicle();
        model.setLicensePlate(vehicleViewModel.getLicensePlate());
        model.setModel(vehicleViewModel.getModel());
        model.setBrand(vehicleViewModel.getBrand());
        return model;
    }
    public Vehicle toModel(VehicleViewModel vehicleViewModel) {
        Vehicle model = new Vehicle();
        model.setVehicleId(vehicleViewModel.getVehicleId());
        model.setLicensePlate(vehicleViewModel.getLicensePlate());
        model.setModel(vehicleViewModel.getModel());
        model.setBrand(vehicleViewModel.getBrand());
        return model;
    }

    public Client toModel(ClientPostViewModel clientViewModel) {
        Client model = new Client();
        model.setName(clientViewModel.getName());
        model.setPhone(clientViewModel.getPhone());
        return model;
    }

    public Accident toModel(AccidentPostViewModel accidentPostViewModel) {
        Accident model = new Accident();
        model.setStatus(accidentPostViewModel.getStatus());
        model.setInsuranceCompany(accidentPostViewModel.getInsuranceCompany());
        model.setLocation(accidentPostViewModel.getLocation());
        return model;
    }

    public Part toModel(PartViewModel partViewModel) {
        Part model = new Part();
        model.setPartId(partViewModel.getPartId());
        model.setKeychain(partViewModel.getKeychain());
        model.setNumberOrder(partViewModel.getNumberOrder());
        model.setWork(partViewModel.getWork());
        model.setMaterials(partViewModel.getMaterials());
        model.setObservations(partViewModel.getObservations());
        model.setLights(partViewModel.isLights());
        model.setBrakeSensors(partViewModel.isBrakeSensors());
        model.setCableTension(partViewModel.isCableTension());
        model.setTirePressure(partViewModel.isTirePressure());
        model.setEngineOil(partViewModel.isEngineOil());
        model.setWear(partViewModel.isWear());
        model.setBrakeFluid(partViewModel.isBrakeFluid());
        model.setBrakePads(partViewModel.isBrakePads());
        model.setTransmissionKit(partViewModel.isTransmissionKit());
        model.setSteeringCondition(partViewModel.isSteeringCondition());
        model.setDynamicTest(partViewModel.isDynamicTest());
        model.setDay1(partViewModel.getDay1());
        model.setDay2(partViewModel.getDay2());
        model.setDay3(partViewModel.getDay3());
        model.setDay4(partViewModel.getDay4());
        model.setDay5(partViewModel.getDay5());
        model.setDay6(partViewModel.getDay6());
        model.setTime1(partViewModel.getTime1());
        model.setTime2(partViewModel.getTime2());
        model.setTime3(partViewModel.getTime3());
        model.setTime4(partViewModel.getTime4());
        model.setTime5(partViewModel.getTime5());
        model.setTime6(partViewModel.getTime6());
        model.setTime7(partViewModel.getTime7());
        model.setTime8(partViewModel.getTime8());
        model.setTime9(partViewModel.getTime9());
        model.setTime10(partViewModel.getTime10());
        model.setTime11(partViewModel.getTime11());
        model.setTime12(partViewModel.getTime12());
        return model;
    }

    public CompanyUser toModel(CompanyUserViewModel companyUserViewModel) {
        CompanyUser model = new CompanyUser();
        model.setCompanyUserId(companyUserViewModel.getCompanyUserId());
        model.setUsername(companyUserViewModel.getUsername());
        //model.setPassword(companyUserViewModel.getPassword());
        model.setRole(companyUserViewModel.getRole());
        model.setEnabled(companyUserViewModel.isEnabled());
        model.setNotes(companyUserViewModel.getNotes()
                .stream().map(this::toModel)
                .collect(Collectors.toList()));
        return model;
    }

    public Note toModel(NoteViewModel noteViewModel) {
        Note model = new Note();
        model.setNoteId(noteViewModel.getNoteId());
        model.setTitle(noteViewModel.getTitle());
        model.setContent(noteViewModel.getContent());
        model.setCreatedAt(noteViewModel.getCreatedAt());
        return model;
    }

    public Note toModel(NotePostViewModel notePostViewModel) {
        Note model = new Note();
        model.setTitle(notePostViewModel.getTitle());
        model.setContent(notePostViewModel.getContent());
        model.setCreatedAt(LocalDateTime.now());
        return model;
    }

    public Appointment toModel(AppointmentPostViewModel appointmentViewModel) {
        Appointment model = new Appointment();
        model.setDateTime(appointmentViewModel.getDateTime());
        model.setTask(appointmentViewModel.getTask());
        return model;
    }

    public CompanyUser toModel(CompanyUserPostViewModel companyUserPostViewModel) {
        CompanyUser model = new CompanyUser();
        model.setUsername(companyUserPostViewModel.getUsername());
        model.setPassword(companyUserPostViewModel.getPassword());
        model.setRole(companyUserPostViewModel.getRole());
        return model;
    }

    public CompanyConfiguration toModel(CompanyConfigurationPostViewModel companyConfigurationPostViewModel) {
        CompanyConfiguration model = new CompanyConfiguration();
        model.setPrimaryColor(companyConfigurationPostViewModel.getPrimaryColor());
        model.setSecondaryColor(companyConfigurationPostViewModel.getSecondaryColor());
        model.setWhatsappApiKey(companyConfigurationPostViewModel.getWhatsappApiKey());
        return model;
    }
}