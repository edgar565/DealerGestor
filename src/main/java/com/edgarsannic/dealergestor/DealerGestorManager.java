/**
 * Proyecto: DealerGestor-Backend
 * Autor: EDGAR SÁNCHEZ NICOLAU
 * Derechos de Autor © 2025
 * Todos los derechos reservados.
 **/

package com.edgarsannic.dealergestor;

import com.edgarsannic.dealergestor.domain.model.*;
import com.edgarsannic.dealergestor.domain.service.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class DealerGestorManager {

    private final AccidentService accidentService;
    private final AppointmentService appointmentService;
    private final ClientService clientService;
    private final CompanyUserService companyUserService;
    private final NoteService noteService;
    private final PartService partService;
    private final RepairService repairService;
    private final VehicleService vehicleService;
    private final CompanyConfigurationService companyConfigurationService;

    public DealerGestorManager(AccidentService accidentService, AppointmentService appointmentService, ClientService clientService, CompanyUserService companyUserService, NoteService noteService, PartService partService, RepairService repairService, VehicleService vehicleService, CompanyConfigurationService companyConfigurationService) {
        this.accidentService = accidentService;
        this.appointmentService = appointmentService;
        this.clientService = clientService;
        this.companyUserService = companyUserService;
        this.noteService = noteService;
        this.partService = partService;
        this.repairService = repairService;
        this.vehicleService = vehicleService;
        this.companyConfigurationService = companyConfigurationService;
    }

    /**
     * ACCIDENT
     **/
    public List<Accident> findAllAccidents() {
        return accidentService.findAllAccidents();
    }

    public List<Accident> findAllAccidentsActive() {
        return accidentService.findAllAccidentsActive();
    }

    public Accident findAccidentById(Long id) {
        return accidentService.findAccidentById(id);
    }

    public Accident saveAccident(Accident accident) {
        return accidentService.saveAccident(accident);
    }

    public Accident updateAccident(Long id, Accident updatedAccident) {
        return accidentService.updateAccident(id, updatedAccident);
    }

    public void deleteAccident(Long id) {
        accidentService.deleteAccident(id);
    }


    /**
     * APPOINTMENT
     **/
    public List<Appointment> findAllAppointments() {
        return appointmentService.findAllAppointments();
    }

    public List<Appointment> findNowAppointments() {
        return appointmentService.findNowAppointments();
    }

    public Appointment findAppointmentById(Long id) {
        return appointmentService.findAppointmentById(id);
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    public Appointment updateAppointment(Long id, Appointment updatedAppointment) {
        return appointmentService.updateAppointment(id, updatedAppointment);
    }

    public void deleteAppointment(Long id) {
        appointmentService.deleteAppointment(id);
    }


    /**
     * CLIENT
     **/
    public List<Client> findAllClients(){
        return clientService.findAllClients();
    }

    public Client findClientById(Long id) {
        return clientService.findClientById(id);
    }

    public Client saveClient(Client client) {
        return clientService.saveClient(client);
    }

    public Client updateClient(Long id, Client updatedClient) {
        return clientService.updateClient(id, updatedClient);
    }

    public void deleteClient(Long id) {
        clientService.deleteClient(id);
    }

    public Client findClientByName(String name) {
        return clientService.findClientByName(name);
    }


    /**
     * COMPANY USER
     **/
    public List<CompanyUser> findAllCompanyUsers() {
        return companyUserService.findAllCompanyUsers();
    }

    public CompanyUser findCompanyUserById(Long id) {
        return companyUserService.findCompanyUserById(id);
    }

    public CompanyUser saveCompanyUser(CompanyUser companyUser) {
        return companyUserService.saveCompanyUser(companyUser);
    }

    public CompanyUser updateCompanyUser(Long id, CompanyUser companyUser) {
        return companyUserService.updateCompanyUser(id, companyUser);
    }

    public void deleteCompanyUser(Long id) {
        companyUserService.deleteCompanyUser(id);
    }

    public CompanyUser findByUsername(String username) {
        return companyUserService.findByUsername(username);
    }


    /**
     * NOTE
     **/
    public List<Note> findAllNotes() {
        return noteService.findAllNotes();
    }

    public Note findNoteById(Long id) {
        return noteService.findNoteById(id);
    }

    public Note saveNote(Note note) {
        return noteService.saveNote(note);
    }

    public Note updateNote(Long id, Note updatedNote) {
        return noteService.updateNote(id, updatedNote);
    }

    public void deleteNote(Long id) {
        noteService.deleteNote(id);
    }


    /**
     * PART
     **/
    public List<Part> findAllParts() {
        return partService.findAllParts();
    }

    public Part findPartById(Long id) {
        return partService.findPartById(id);
    }

    public Part savePart(Part part) {
        return partService.savePart(part);
    }

    public Part updatePart(Long id, Part updatedPart) {
        return partService.updatePart(id, updatedPart);
    }

    public void deletePart(@PathVariable Long id) {
        partService.deletePart(id);
    }


    /**
     * REPAIR
     **/
    public List<Repair> findAllRepairs() {
        return repairService.findAllRepairs();
    }

    public List<Repair> findAllRepairsActive() {
        return repairService.findAllRepairsActive();
    }

    public Repair findRepairById(Long id) {
        return repairService.findRepairById(id);
    }

    public Repair saveRepair(Repair repair) {
        return repairService.saveRepair(repair);
    }

    public Repair updateRepair(Long id, Repair updatedRepair) {
        return repairService.updateRepair(id, updatedRepair);
    }

    public void deleteRepair(Long id) {
        repairService.deleteRepair(id);
    }


    /**
     *  VEHICLE
     **/
    public List<Vehicle> findAllVehicles() {
        return vehicleService.findAllVehicles();
    }

    public Vehicle findVehicleById(Long id) {
        return vehicleService.findVehicleById(id);
    }

    public Vehicle findVehicleByLicensePlate(String licensePlate) {
        return vehicleService.findVehicleByLicensePlate(licensePlate);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleService.saveVehicle(vehicle);
    }

    public Vehicle updateVehicle(Long id, Vehicle vehicle) {
        return vehicleService.updateVehicle(id, vehicle);
    }

    public void deleteVehicle(Long id) {
        vehicleService.deleteVehicle(id);
    }


    /**
     * COMPANY
     **/
    public CompanyConfiguration findCompanyConfiguration(Long id) {
        return companyConfigurationService.findCompanyConfiguration(id);
    }
    public CompanyConfiguration updateCompanyConfiguration(CompanyConfiguration request, MultipartFile logoFile) {
        return companyConfigurationService.updateCompanyConfiguration(request, logoFile);
    }
}