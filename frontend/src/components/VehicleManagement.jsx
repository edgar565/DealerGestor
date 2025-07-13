import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table.jsx';
import '../styles/management.css';
import { Button } from './ui/Button.jsx';
import axios from 'axios';
import { getApiUrl } from '../services/api.js';

const VehicleManagement = () => {
    const [vehicles, setVehicles] = useState([]);
    const [searchLicensePlate, setSearchLicensePlate] = useState('');
    const [searchClientName, setSearchClientName] = useState('');
    const [clients, setClients] = useState([]);
    const [selectedClientId, setSelectedClientId] = useState('');
    const [clientSearch, setClientSearch] = useState('');

    useEffect(() => {
        fetchVehicles();
    }, []);

    const fetchVehicles = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(getApiUrl('/vehicles'), {
                headers: { Authorization: `Bearer ${token}` },
            });
            setVehicles(response.data);
        } catch (error) {
            console.error('Error fetching vehicles:', error);
        }
    };

    const fetchClients = async (query) => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(getApiUrl(`/clients/search?query=${query}`), {
                headers: { Authorization: `Bearer ${token}` },
            });
            setClients(response.data);
        } catch (error) {
            console.error('Error fetching clients:', error);
        }
    };

    const handleSaveVehicle = async (e) => {
        e.preventDefault();
        const form = e.target;
        const vehicle = {
            licensePlate: form.licensePlate.value,
            brand: form.brand.value,
            model: form.model.value,
            clientId: selectedClientId,
        };

        try {
            const token = localStorage.getItem('token');
            await axios.post(getApiUrl('/vehicles/save'), vehicle, {
                headers: { Authorization: `Bearer ${token}` },
            });
            form.reset();
            setSelectedClientId('');
            fetchVehicles();
        } catch (error) {
            console.error('Error saving vehicle:', error);
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        try {
            const token = localStorage.getItem('token');
            if (searchLicensePlate) {
                const response = await axios.get(getApiUrl(`/vehicles/licensePlate/${searchLicensePlate}`), {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setVehicles(response.data);
            } else if (searchClientName) {
                const response = await axios.get(getApiUrl(`/vehicles/client/${searchClientName}`), {
                    headers: { Authorization: `Bearer ${token}` },
                });
                setVehicles(response.data);
            } else {
                fetchVehicles();
            }
        } catch (error) {
            console.error('Error searching vehicles:', error);
        }
    };

    const handleClientSearch = async (e) => {
        const value = e.target.value;
        setClientSearch(value);
        if (value.length >= 2) {
            fetchClients(value);
        } else {
            setClients([]);
        }
    };

    const handleDeleteVehicle = async (vehicleId) => {
        if (!window.confirm('¿Seguro que quieres eliminar este vehículo?')) return;

        try {
            const token = localStorage.getItem('token');
            await axios.delete(getApiUrl(`/vehicles/delete/${vehicleId}`), {
                headers: { Authorization: `Bearer ${token}` },
            });
            fetchVehicles();
        } catch (error) {
            console.error('Error deleting vehicle:', error);
        }
    };


    const headers = ["MATRÍCULA", "MARCA", "MODELO", "CLIENTE", "ACCIONES"];
    const rows = vehicles.map(vehicle => [
        vehicle.licensePlate,
        vehicle.brand,
        vehicle.model,
        vehicle.clientName || '—',
        <Button
            key={`delete-${vehicle.id}`}
            className="btn btn-sm btn-outline-danger"
            onClick={() => handleDeleteVehicle(vehicle.id)}
        >
            Eliminar
        </Button>
    ]);

    const leftButton = [
        <Button key="addVehicleBtn" data-bs-toggle="modal" data-bs-target="#vehicleFormModal">
            <i className="fa-solid fa-plus me-2"></i>AÑADIR VEHÍCULO
        </Button>
    ];

    const rightButton = [
        <Button key="searchVehicleBtn" variant="secondary" data-bs-toggle="modal" data-bs-target="#searchModal">
            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR VEHÍCULO
        </Button>
    ];

    return (
        <div className="container">
            <Header title="GESTIÓN DE VEHÍCULOS" leftButton={leftButton} rightContent={rightButton} />

            {/* Modal para añadir vehículo */}
            <Modal id="vehicleFormModal" title="Añadir Vehículo">
                <form id="vehicleForm" onSubmit={handleSaveVehicle}>
                    <div className="form-group mb-3">
                        <label htmlFor="licensePlate" className="form-label">MATRÍCULA</label>
                        <input type="text" className="form-control border-black" id="licensePlate" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="brand" className="form-label">MARCA</label>
                        <input type="text" className="form-control border-black" id="brand" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="model" className="form-label">MODELO</label>
                        <input type="text" className="form-control border-black" id="model" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="clientSearch" className="form-label">CLIENTE</label>
                        <input
                            type="text"
                            className="form-control border-black mb-2"
                            placeholder="Buscar cliente"
                            value={clientSearch}
                            onChange={handleClientSearch}
                        />
                        <select
                            className="form-control border-black"
                            onChange={(e) => setSelectedClientId(e.target.value)}
                            required
                        >
                            <option value="" disabled selected>Selecciona un cliente</option>
                            {clients.map(client => (
                                <option key={client.id} value={client.id}>{client.name}</option>
                            ))}
                        </select>
                    </div>

                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            {/* Modal para búsqueda */}
            <Modal id="searchModal" title="Buscar Vehículo">
                <form id="searchVehicle" onSubmit={handleSearch}>
                    <div className="form-group mb-3">
                        <label htmlFor="searchLicensePlate" className="form-label">MATRÍCULA</label>
                        <input
                            type="text"
                            className="form-control border-black"
                            value={searchLicensePlate}
                            onChange={(e) => setSearchLicensePlate(e.target.value)}
                            placeholder="Ingrese matrícula"
                        />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="searchClientName" className="form-label">CLIENTE</label>
                        <input
                            type="text"
                            className="form-control border-black"
                            value={searchClientName}
                            onChange={(e) => setSearchClientName(e.target.value)}
                            placeholder="Ingrese nombre cliente"
                        />
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">
                            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR
                        </button>
                    </div>
                </form>
            </Modal>

            <Table headers={headers} rows={rows} id="vehiclesTable" />
        </div>
    );
};

export default VehicleManagement;
