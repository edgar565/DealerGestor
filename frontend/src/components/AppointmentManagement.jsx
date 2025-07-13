import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table.jsx';
import '../styles/management.css';
import { Button } from './ui/Button.jsx';
import axios from 'axios';
import { getApiUrl } from '../services/api.js';

const AppointmentManagement = () => {
    const [appointments, setAppointments] = useState([]);
    const [searchMatricula, setSearchMatricula] = useState('');
    const [searchClient, setSearchClient] = useState('');
    const [vehicles, setVehicles] = useState([]);
    const [vehicleSearch, setVehicleSearch] = useState('');
    const [selectedVehicleId, setSelectedVehicleId] = useState('');


    useEffect(() => {
        fetchAppointments();
    }, []);

    const fetchAppointments = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(getApiUrl('/appointments'), {
                headers: { Authorization: `Bearer ${token}` }
            });
            setAppointments(response.data);
        } catch (error) {
            console.error('Error fetching appointments:', error);
        }
    };

    const handleSaveAppointment = async (e) => {
        e.preventDefault();
        const form = e.target;
        const appointment = {
            dateTime: form.fechaHora.value,
            task: form.tarea.value,
            vehicleId: selectedVehicleId
        };

        try {
            const token = localStorage.getItem('token');
            await axios.post(getApiUrl('/appointments/save'), appointment, {
                headers: { Authorization: `Bearer ${token}` }
            });
            form.reset();
            fetchAppointments();
        } catch (error) {
            console.error('Error saving appointment:', error);
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        const token = localStorage.getItem('token');
        try {
            let response;
            if (searchMatricula) {
                response = await axios.get(getApiUrl(`/appointments/vehicle/${searchMatricula}`), {
                    headers: { Authorization: `Bearer ${token}` }
                });
            } else if (searchClient) {
                response = await axios.get(getApiUrl(`/appointments/client/${searchClient}`), {
                    headers: { Authorization: `Bearer ${token}` }
                });
            } else {
                fetchAppointments();
                return;
            }
            setAppointments(response.data);
        } catch (error) {
            console.error('Error searching appointments:', error);
        }
    };

    const handleVehicleSearch = async (e) => {
        const value = e.target.value;
        setVehicleSearch(value);
        if (value.length >= 2) {
            try {
                const token = localStorage.getItem('token');
                const response = await axios.get(getApiUrl(`/vehicles/search/${value}`), {
                    headers: { Authorization: `Bearer ${token}` }
                });
                setVehicles(response.data);
            } catch (error) {
                console.error('Error searching vehicles:', error);
            }
        }
    };

    const headers = ["FECHA", "HORA", "CLIENTE", "VEHÍCULO", "TAREA", "ACCIONES"];
    const rows = appointments.map(app => [
        app.dateTime.split('T')[0],
        app.dateTime.split('T')[1],
        app.nameClient,
        app.licensePlate,
        app.task,
        <Button className="btn btn-sm btn-outline-danger">Eliminar</Button>
    ]);

    const leftButton = [
        <Button data-bs-toggle="modal" data-bs-target="#citaFormModal">
            <i className="fa-solid fa-plus me-2"></i>AÑADIR CITA
        </Button>
    ];

    const rightButton = [
        <Button variant={"secondary"} data-bs-toggle="modal" data-bs-target="#searchModal">
            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR CITA
        </Button>
    ];

    return (
        <div className="container">
            <Header title="GESTIÓN DE CITAS" leftButton={leftButton} rightContent={rightButton} />

            <Modal id="citaFormModal" title="Añadir Cita">
                <form id="citaForm" onSubmit={handleSaveAppointment}>
                    <div className="form-group mb-3">
                        <label htmlFor="fechaHora" className="form-label">FECHA Y HORA</label>
                        <input type="datetime-local" className="form-control border-black" id="fechaHora" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="citaMarcaModelo" className="form-label">VEHÍCULO</label>
                        <input type="text" className="form-control border-black mb-2" placeholder="Buscar matrícula" value={vehicleSearch} onChange={handleVehicleSearch} />
                        <select className="form-control border-black" onChange={(e) => setSelectedVehicleId(e.target.value)} required>
                            <option value="" disabled selected>Selecciona un vehículo</option>
                            {vehicles.map(v => (
                                <option key={v.id} value={v.id}>{v.licensePlate} - {v.brand} {v.model}</option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="tarea" className="form-label">TAREA</label>
                        <select id="tarea" className="form-control border-black" required>
                            <option value="" disabled selected>Selecciona una tarea</option>
                            <option value="REVISIÓN GENERAL">REVISIÓN GENERAL</option>
                            <option value="SUSTITUCIÓN DE NEUMÁTICOS">SUSTITUCIÓN DE NEUMÁTICOS</option>
                            <option value="LARGA DURACIÓN">LARGA DURACIÓN</option>
                        </select>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="searchModal" title="Buscar Cita">
                <form id="searchCita" onSubmit={handleSearch}>
                    <div className="form-group mb-3">
                        <label htmlFor="searchMatricula" className="form-label">MATRÍCULA</label>
                        <input type="text" className="form-control border-black" value={searchMatricula} onChange={(e) => setSearchMatricula(e.target.value)} placeholder="Por favor ingrese la matrícula" />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="searchClient" className="form-label">NOMBRE</label>
                        <input type="text" className="form-control border-black" value={searchClient} onChange={(e) => setSearchClient(e.target.value)} placeholder="Por favor ingrese el nombre" />
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success"><i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR</button>
                    </div>
                </form>
            </Modal>

            <Table headers={headers} rows={rows} id="citasTable" />
        </div>
    );
};

export default AppointmentManagement;