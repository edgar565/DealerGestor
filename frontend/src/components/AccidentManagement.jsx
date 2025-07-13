import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table';
import '../styles/management.css';
import { Button } from './ui/Button.jsx';
import axios from 'axios';
import { getApiUrl } from '../services/api.js';


const AccidentManagement = () => {
    const [accidents, setAccidents] = useState([]);

    useEffect(() => {
        fetchAccidents();
    }, []);

    const fetchAccidents = async () => {
        const token = localStorage.getItem('token');
        try {
            const response = await axios.get(getApiUrl('/accidents'), {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            setAccidents(response.data);
        } catch (error) {
            console.error('Error fetching accidents:', error);
        }
    };

    const saveAccident = async (event) => {
        event.preventDefault();
        const token = localStorage.getItem('token');
        const formData = new FormData(event.target);
        const data = Object.fromEntries(formData.entries());

        try {
            await axios.post(getApiUrl('/accidents/save'), data, {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            fetchAccidents();
            document.querySelector('#accidentFormModal .btn-close').click();
        } catch (error) {
            console.error('Error saving accident:', error);
        }
    };

    const confirmDelete = async () => {
        const token = localStorage.getItem('token');
        const id = document.getElementById('accidentId').value;
        try {
            await axios.delete(getApiUrl(`/accidents/${id}`), {
                headers: {
                    'Authorization': `Bearer ${token}`,
                },
            });
            fetchAccidents();
            document.querySelector('#confirmModal .btn-close').click();
        } catch (error) {
            console.error('Error deleting accident:', error);
        }
    };

    const leftButton = [
        <Button data-bs-toggle="modal" data-bs-target="#accidentFormModal">
            <i className="fa-solid fa-plus me-2"></i>AÑADIR SINIESTRO
        </Button>
    ];

    const rightButton = [
        <Button variant={"secondary"} data-bs-toggle="modal" data-bs-target="#searchModal">
            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR SINIESTRO
        </Button>
    ];

    const headers = ["FECHA", "MATRÍCULA", "VEHÍCULO", "SEGURO", "OPERARIO", "ESTADO", "UBICACIÓN", "PARTE", "ACCIONES"];

    return (
        <div className="container">
            <Header title="GESTIÓN DE SINIESTROS" leftButton={leftButton} rightContent={rightButton} />

            <Modal id="accidentFormModal" title="Añadir/Editar Siniestro">
                <form id="accidentForm" onSubmit={saveAccident}>
                    <input type="hidden" id="accidentId" name="id" />
                    <div className="form-group mb-3">
                        <label htmlFor="date" className="form-label">FECHA</label>
                        <input type="date" className="form-control border-black" id="date" name="date" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="citaMarcaModelo" className="form-label">VEHÍCULO</label>
                        <select id="citaMarcaModelo" name="vehicleId" className="form-control border-black" required>
                            <option value="" disabled selected>Selecciona un vehículo</option>
                        </select>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="operator" className="form-label">OPERARIO</label>
                        <select id="operator" name="operator" className="form-control border-black" required>
                            <option value="" disabled selected>Selecciona un operario</option>
                            <option value="JOSE">JOSE</option>
                            <option value="SAID">SAID</option>
                            <option value="VICENTE">VICENTE</option>
                        </select>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="status" className="form-label">ESTADO</label>
                        <select id="status" name="status" className="form-control border-black" required>
                            <option value="" disabled selected>Selecciona un estado</option>
                            <option value="PENDIENTE PERITACIÓN">PENDIENTE PERITACIÓN</option>
                            <option value="PERITADA">PERITADA</option>
                            <option value="PENDIENTE ACEPTACIÓN">PENDIENTE ACEPTACIÓN</option>
                            <option value="ACEPTADA">ACEPTADA</option>
                            <option value="PEDIDO MATERIAL">PEDIDO MATERIAL</option>
                            <option value="EN REPARACIÓN">EN REPARACIÓN</option>
                            <option value="FINALIZADA">FINALIZADA</option>
                        </select>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="insuranceCompany" className="form-label">SEGURO</label>
                        <input type="text" className="form-control border-black" id="insuranceCompany" name="insuranceCompany" placeholder="Nombre de la compañía" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="location" className="form-label">UBICACIÓN</label>
                        <select id="location" name="location" className="form-control border-black" required>
                            <option value="" disabled selected>Selecciona una ubicación</option>
                            <option value="CLIENTE">CLIENTE</option>
                            <option value="TALLER">TALLER</option>
                        </select>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="searchModal" title="Buscar Reparación">
                <form id="search">
                    <div className="form-group mb-3">
                        <label htmlFor="searchMatricula" className="form-label">MATRÍCULA</label>
                        <input type="text" className="form-control border-black" id="searchMatricula" placeholder="Por favor ingrese la matrícula" />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="searchAseguradora" className="form-label">ASEGURADORA</label>
                        <input type="text" className="form-control border-black" id="searchAseguradora" placeholder="Por favor ingrese la aseguradora" />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="keychain" className="form-label">LLAVERO</label>
                        <input type="text" className="form-control border-black" id="keychain" placeholder="Por favor acerque el llavero" />
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success"><i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="partFormModal" title="Crear Parte">
                <form id="partForm">
                    <div className="form-group mb-3">
                        <label htmlFor="numberorden" className="form-label">NÚMERO DE ORDEN</label>
                        <input type="text" className="form-control border-black" id="numberorden" placeholder="Por favor ingrese el número de la orden" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="work" className="form-label">TRABAJOS A REALIZAR</label>
                        <textarea className="form-control border-black" id="work" placeholder="Por favor ingrese los trabajos a realizar" required></textarea>
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="llavero" className="form-label">LLAVERO</label>
                        <input type="text" className="form-control border-black" id="llavero" placeholder="Por favor acerque el llavero" required />
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR VEHÍCULO</button>
                    </div>
                </form>
            </Modal>

            <Modal id="confirmModal" title="¿ESTÁS SEGURO DE ELIMINAR ESTE SINIESTRO?">
                <p id="motoInfo"></p>
                <div className="modal-footer">
                    <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">CANCELAR</button>
                    <button type="button" className="btn btn-danger" id="confirmDeleteBtn" onClick={confirmDelete}>ACEPTAR</button>
                </div>
            </Modal>

            <Table headers={headers} id="accidentsTable" data={accidents} />
        </div>
    );
};

export default AccidentManagement;
