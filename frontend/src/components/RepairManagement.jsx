import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table';
import '../styles/management.css';
import { Button } from "./ui/Button.jsx";
import axios from 'axios';
import { getApiUrl } from '../services/api.js';

const RepairManagement = () => {
    const [repairs, setRepairs] = useState([]);
    const [vehicles, setVehicles] = useState([]);
    const [operators, setOperators] = useState([]);
    const [selectedRepair, setSelectedRepair] = useState(null);

    const token = localStorage.getItem('token');
    const authHeader = { headers: { Authorization: `Bearer ${token}` } };

    useEffect(() => {
        fetchRepairs();
        fetchVehicles();
        fetchOperators();
    }, []);

    const fetchRepairs = async () => {
        try {
            const response = await axios.get(getApiUrl('/repairs'), authHeader);
            setRepairs(response.data);
        } catch (error) {
            console.error('Error al cargar reparaciones:', error);
        }
    };

    const fetchVehicles = async () => {
        try {
            const response = await axios.get(getApiUrl('/vehicles'), authHeader);
            setVehicles(response.data);
        } catch (error) {
            console.error('Error al cargar vehículos:', error);
        }
    };

    const fetchOperators = async () => {
        try {
            const response = await axios.get(getApiUrl('/companyusers/role/MECHANIC'), authHeader);
            setOperators(response.data);
        } catch (error) {
            console.error('Error al cargar operarios:', error);
        }
    };

    const saveRepair = async (e) => {
        e.preventDefault();
        const id = document.getElementById('repairId').value;
        const repair = {
            date: document.getElementById('date').value,
            vehicleId: document.getElementById('vehicleSelect').value,
            operatorId: document.getElementById('operatorSelect').value,
            status: document.getElementById('status').value
        };

        try {
            const url = id ? getApiUrl(`/repairs/update/${id}`) : getApiUrl('/repairs/save');
            const method = id ? axios.put : axios.post;
            await method(url, repair, authHeader);
            await fetchRepairs();
            e.target.reset();
        } catch (error) {
            console.error('Error al guardar reparación:', error);
        }
    };

    const search = async (e) => {
        e.preventDefault();
        const plate = document.getElementById('searchMatricula').value;
        const keychain = document.getElementById('keychain').value;

        try {
            let endpoint = '/repairs';
            if (plate) endpoint = `/repairs/vehicle/${plate}`;
            else if (keychain) endpoint = `/repairs/keychain/${keychain}`;

            const response = await axios.get(getApiUrl(endpoint), authHeader);
            setRepairs(response.data);
        } catch (error) {
            console.error('Error al buscar reparaciones:', error);
        }
    };

    const confirmDelete = async () => {
        if (!selectedRepair) return;
        try {
            await axios.delete(getApiUrl(`/repairs/delete/${selectedRepair.id}`), authHeader);
            await fetchRepairs();
        } catch (error) {
            console.error('Error al eliminar reparación:', error);
        }
    };

    const headers = ["FECHA", "MATRÍCULA", "MARCA", "MODELO", "OPERARIO", "ESTADO", "PARTE", "ACCIONES"];

    return (
        <div className="container">
            <Header title="GESTIÓN DE REPARACIONES"
                    leftButton={[
                        <Button key="addRepair" data-bs-toggle="modal" data-bs-target="#repairFormModal" onClick={() => setSelectedRepair(null)}>
                            <i className="fa-solid fa-plus me-2"></i>AÑADIR REPARACIÓN
                        </Button>
                    ]}
                    rightContent={[
                        <Button key="searchRepair" variant={"secondary"} data-bs-toggle="modal" data-bs-target="#searchModal">
                            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR REPARACIÓN
                        </Button>
                    ]}
            />

            <Modal id="repairFormModal" title="Añadir/Editar Reparación">
                <form id="repairForm" onSubmit={saveRepair}>
                    <input type="hidden" id="repairId" />
                    <div className="form-group mb-3">
                        <label htmlFor="date" className="form-label">FECHA</label>
                        <input type="date" className="form-control border-black" id="date" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="vehicleSelect" className="form-label">VEHÍCULO</label>
                        <select id="vehicleSelect" className="form-control border-black" required>
                            <option value="" disabled>Selecciona un vehículo</option>
                            {vehicles.map(vehicle => (
                                <option key={vehicle.id} value={vehicle.id}>
                                    {vehicle.licensePlate} - {vehicle.brand} {vehicle.model}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="operatorSelect" className="form-label">OPERARIO</label>
                        <select id="operatorSelect" className="form-control border-black" required>
                            <option value="" disabled>Selecciona un operario</option>
                            {operators.map(op => (
                                <option key={op.companyUserId} value={op.companyUserId}>
                                    {op.username}
                                </option>
                            ))}
                        </select>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="status" className="form-label">ESTADO</label>
                        <select id="status" className="form-control border-black" required>
                            <option value="" disabled>Selecciona un estado</option>
                            <option value="EN_REPARACIÓN">EN REPARACIÓN</option>
                            <option value="PEDIDO_RECAMBIO">PEDIDO RECAMBIO</option>
                            <option value="FINALIZADA_CONTACTO">FINALIZADA - CONTACTO</option>
                            <option value="FINALIZADA">FINALIZADA</option>
                            <option value="CIERRE">CIERRE</option>
                        </select>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="searchModal" title="Buscar Reparación">
                <form id="search" onSubmit={search}>
                    <div className="form-group mb-3">
                        <label htmlFor="searchMatricula" className="form-label">MATRÍCULA</label>
                        <input type="text" className="form-control border-black" id="searchMatricula" />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="keychain" className="form-label">LLAVERO</label>
                        <input type="text" className="form-control border-black" id="keychain" />
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success"><i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="confirmModal" title="¿ESTÁS SEGURO DE ELIMINAR ESTA REPARACIÓN?">
                <p id="motoInfo"></p>
                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">CANCELAR</button>
                    <button type="button" className="btn btn-danger" onClick={confirmDelete}>ELIMINAR</button>
                </div>
            </Modal>

            <Table headers={headers} id="repairsTable" data={repairs} />
        </div>
    );
};

export default RepairManagement;
