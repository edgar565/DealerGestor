import React, { useEffect, useState } from 'react';
import Header from './ui/Header';
import Modal from './ui/Modal';
import Table from './ui/Table.jsx';
import '../styles/management.css';
import { Button } from './ui/Button.jsx';
import axios from 'axios';
import { getApiUrl } from '../services/api.js';

const CompanyUserManagement = () => {
    const [users, setUsers] = useState([]);
    const [searchName, setSearchName] = useState('');
    const [searchEmail, setSearchEmail] = useState('');


    useEffect(() => {
        fetchUsers();
    }, []);

    const fetchUsers = async () => {
        try {
            const token = localStorage.getItem('token');
            const response = await axios.get(getApiUrl('/company-users'), {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            setUsers(response.data);
        } catch (error) {
            console.error('Error fetching users:', error);
        }
    };

    const handleSaveUser = async (e) => {
        e.preventDefault();
        const form = e.target;
        const newUser = {
            name: form.name.value,
            email: form.email.value,
            password: form.password.value,
            role: form.role.value
        };

        try {
            const token = localStorage.getItem('token');
            await axios.post(getApiUrl('/company-users/register'), newUser, {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            });
            form.reset();
            fetchUsers();
            window.bootstrap.Modal.getInstance(document.getElementById('userFormModal')).hide();
        } catch (error) {
            console.error('Error saving user:', error);
        }
    };

    const handleSearch = async (e) => {
        e.preventDefault();
        if (searchName || searchEmail) {
            const filtered = users.filter(user =>
                user.name.toLowerCase().includes(searchName.toLowerCase()) &&
                user.email.toLowerCase().includes(searchEmail.toLowerCase())
            );
            setUsers(filtered);
        } else {
            fetchUsers();
        }
    };


    const headers = ["NOMBRE", "EMAIL", "ROL", "ACCIONES"];
    const rows = users.map(user => [
        user.name,
        user.email,
        user.role,
        <Button className="btn btn-sm btn-outline-danger" onClick={() => handleDeleteUser(user.id)}>Eliminar</Button>
    ]);

    const handleDeleteUser = async (id) => {
        if (window.confirm('¿Seguro que quieres eliminar este usuario?')) {
            try {
                await axios.delete(getApiUrl(`/company-users/delete/${id}`));
                fetchUsers();
            } catch (error) {
                console.error('Error deleting user:', error);
            }
        }
    };

    const leftButton = [
        <Button data-bs-toggle="modal" data-bs-target="#userFormModal">
            <i className="fa-solid fa-plus me-2"></i>AÑADIR USUARIO
        </Button>
    ];

    const rightButton = [
        <Button variant={"secondary"} data-bs-toggle="modal" data-bs-target="#searchModal">
            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR USUARIO
        </Button>
    ];

    return (
        <div className="container">
            <Header title="GESTIÓN DE USUARIOS" leftButton={leftButton} rightContent={rightButton} />

            <Modal id="userFormModal" title="Añadir Usuario">
                <form id="userForm" onSubmit={handleSaveUser}>
                    <div className="form-group mb-3">
                        <label htmlFor="name" className="form-label">NOMBRE</label>
                        <input type="text" className="form-control border-black" id="name" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="email" className="form-label">EMAIL</label>
                        <input type="email" className="form-control border-black" id="email" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="password" className="form-label">CONTRASEÑA</label>
                        <input type="password" className="form-control border-black" id="password" required />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="role" className="form-label">ROL</label>
                        <select id="role" className="form-control border-black" required defaultValue="">
                            <option value="" disabled>Selecciona un rol</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="MECHANIC">MECHANIC</option>
                            <option value="RECEPTIONIST">RECEPTIONIST</option>
                        </select>
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">GUARDAR</button>
                    </div>
                </form>
            </Modal>

            <Modal id="searchModal" title="Buscar Usuario">
                <form id="searchUser" onSubmit={handleSearch}>
                    <div className="form-group mb-3">
                        <label htmlFor="searchName" className="form-label">NOMBRE</label>
                        <input type="text" className="form-control border-black" value={searchName} onChange={(e) => setSearchName(e.target.value)} placeholder="Ingrese el nombre" />
                    </div>
                    <div className="form-group mb-3">
                        <label htmlFor="searchEmail" className="form-label">EMAIL</label>
                        <input type="email" className="form-control border-black" value={searchEmail} onChange={(e) => setSearchEmail(e.target.value)} placeholder="Ingrese el email" />
                    </div>
                    <div className="modal-footer d-flex justify-content-between">
                        <button type="button" className="btn btn-danger" data-bs-dismiss="modal">CANCELAR</button>
                        <button type="submit" className="btn btn-success">
                            <i className="fa-solid fa-magnifying-glass me-2"></i>BUSCAR
                        </button>
                    </div>
                </form>
            </Modal>

            <Table headers={headers} rows={rows} id="usersTable" />
        </div>
    );
};

export default CompanyUserManagement;
